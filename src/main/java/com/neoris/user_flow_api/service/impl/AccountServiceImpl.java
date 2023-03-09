package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.AccountType;
import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.AccountMapper;
import com.neoris.user_flow_api.repository.AccountRepository;
import com.neoris.user_flow_api.repository.CustomerRepository;
import com.neoris.user_flow_api.service.AccountService;
import com.neoris.user_flow_api.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

  public final AccountRepository accountRepository;
  public final CustomerRepository customerRepository;

  public AccountServiceImpl(
      AccountRepository accountRepository, CustomerRepository customerRepository) {
    this.accountRepository = accountRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  @Transactional
  public List<AccountDTO> createAccount(List<AccountDTO> accountDTOs) throws UserFlowException {

    if (accountDTOs.stream().anyMatch(
        accountDTO -> !accountDTO.getAccountType().equals(AccountType.CORRIENTE.name()) && !accountDTO.getAccountType()
            .equals(AccountType.AHORRO.name()))) {
      throw new UserFlowException(NotificationCode.INVALID_ACCOUNT_TYPE);
    }

    List<Account> accountsToSaved = new ArrayList<>();
    for (AccountDTO accountDTO : accountDTOs) {
      Customer customer = customerRepository.findById(accountDTO.getCustomerId()).orElse(null);

      if (customer != null) {
        Account account = AccountMapper.INSTANCE.accountDTOToAccount(accountDTO);
        account.setCustomer(customer);
        accountsToSaved.add(account);
      } else {
        throw new UserFlowException(NotificationCode.CUSTOMER_NOT_FOUND);
      }

    }
    try {
      log.debug("Saving accounts");
      List<Account> savedAccounts = accountRepository.saveAll(accountsToSaved);
      log.info("{} Accounts created", savedAccounts.size());

      return savedAccounts.stream()
          .map(AccountMapper.INSTANCE::accountToAccountDTO)
          .toList();
    } catch (Exception e) {
      log.error("An error occurred while saving the data.", e);
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  @Override
  @Transactional
  public AccountDTO updateAccount(AccountDTO accountDTO, Long id) throws UserFlowException {
    validateAccountType(accountDTO);
    Account accountFound = findById(id);
    AccountMapper.INSTANCE.updateAccountFromAccountDTO(accountDTO, accountFound);
    try {
      Account updatedAccount = accountRepository.save(accountFound);
      log.info("{} Account updated", id);
      return AccountMapper.INSTANCE.accountToAccountDTO(updatedAccount);
    } catch (Exception e) {
      log.debug("{} Error updating account", id);
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA);
    }
  }

  public Account findById(Long id) throws UserFlowException {
    Account account = accountRepository.findById(id).orElse(null);

    if (account == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ID_NOT_FOUND);
    }
    return account;
  }

  @Override
  public AccountDTO getAccountById(Long id) throws UserFlowException {
    return AccountMapper.INSTANCE.accountToAccountDTO(findById(id));
  }

  @Override
  public void deleteAccount(Long id) throws UserFlowException {
    Account account = findById(id);
    accountRepository.delete(account);
    log.info("{} Account deleted", id);
  }

  private void validateAccountType(AccountDTO accountDTO) throws UserFlowException {
    String accountType = accountDTO.getAccountType();
    if (!accountType.equalsIgnoreCase(AccountType.CORRIENTE.name())
        && !accountType.equalsIgnoreCase(AccountType.AHORRO.name())) {
      throw new UserFlowException(NotificationCode.INVALID_ACCOUNT_TYPE);
    }
  }

}
