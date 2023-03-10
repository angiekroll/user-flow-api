package com.neoris.user_flow_api.delegate.impl;

import com.neoris.user_flow_api.constans.AccountType;
import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.delegate.AccountDelegate;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.AccountMapper;
import com.neoris.user_flow_api.service.AccountService;
import com.neoris.user_flow_api.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountDelegateImpl implements AccountDelegate {

  public final AccountService accountService;
  public final CustomerService customerService;

  public AccountDelegateImpl(AccountService accountService, CustomerService customerService) {
    this.accountService = accountService;
    this.customerService = customerService;
  }


  @Override
  @Transactional
  public List<AccountDTO> createAccount(List<AccountDTO> accountDTOs) throws UserFlowException {

    validateAccountType(accountDTOs);

    List<Account> accountsToSaved = new ArrayList<>();
    for (AccountDTO accountDTO : accountDTOs) {
      Customer customer = customerService.findById(accountDTO.getCustomerId());

      Account account = AccountMapper.INSTANCE.accountDTOToAccount(accountDTO);
      account.setCustomer(customer);
      accountsToSaved.add(account);
    }

    List<Account> savedAccounts = accountService.saveAll(accountsToSaved);
    log.info("{} accounts created", savedAccounts.size());

    return savedAccounts.stream()
        .map(AccountMapper.INSTANCE::accountToAccountDTO)
        .toList();
  }

  @Override
  @Transactional
  public AccountDTO updateAccount(AccountDTO accountDTO, Long id) throws UserFlowException {
    List<AccountDTO> AccountDTOs = new ArrayList<>();
    AccountDTOs.add(accountDTO);
    validateAccountType(AccountDTOs);
    Account accountFound = accountService.findById(id);

    AccountMapper.INSTANCE.updateAccountFromAccountDTO(accountDTO, accountFound);
    Account updatedAccount = accountService.save(accountFound);
    log.info("{} account updated", id);

    return AccountMapper.INSTANCE.accountToAccountDTO(updatedAccount);
  }

  @Override
  public AccountDTO getAccountById(Long accountNumber) throws UserFlowException {
    Account account = accountService.findById(accountNumber);
    return AccountMapper.INSTANCE.accountToAccountDTO(account);
  }

  @Override
  public void deleteAccount(Long id) throws UserFlowException {
    Account account = accountService.findById(id);
    accountService.delete(account);
    log.info("{} account deleted", id);
  }

  private void validateAccountType(List<AccountDTO> accountDTOs) throws UserFlowException {
    if (accountDTOs.stream().anyMatch(
        accountDTO -> !accountDTO.getAccountType().equalsIgnoreCase(AccountType.CORRIENTE.name())
            && !accountDTO.getAccountType()
            .equalsIgnoreCase(AccountType.AHORRO.name()))) {
      throw new UserFlowException(NotificationCode.INVALID_ACCOUNT_TYPE);
    }
  }

}
