package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.repository.AccountRepository;
import com.neoris.user_flow_api.repository.CustomerRepository;
import com.neoris.user_flow_api.service.AccountService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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

  public Account findById(Long id) throws UserFlowException {
    Account account = accountRepository.findById(id).orElse(null);

    if (account == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ID_NOT_FOUND);
    }
    return account;
  }

  @Override
  public Account findByAccountNumberAndStateTrue(Long accountNumber) throws UserFlowException {
    Account account = accountRepository.findByAccountNumberAndStateTrue(accountNumber).orElse(null);
    if (account == null) {
      log.error("There is no active account");
      throw new UserFlowException(NotificationCode.ACCOUNT_NOT_FOUND);
    }
    return account;
  }

  @Override
  public Account save(Account account) throws UserFlowException {
    try {
      log.info("Saving account");
      return accountRepository.save(account);
    } catch (Exception e) {
      log.error("Error Saving account.");
      throw new UserFlowException(NotificationCode.ERROR_SAVING_DATA, e);
    }
  }

  @Override
  public List<Account> saveAll(List<Account> accounts) throws UserFlowException {
    try {
      log.info("Saving accounts");
      return accountRepository.saveAll(accounts);
    } catch (Exception e) {
      log.error("Error Saving accounts.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  @Override
  public void delete(Account account) throws UserFlowException {
    try {
      log.debug("Deleting account");
      accountRepository.delete(account);
    } catch (Exception e) {
      log.error("Error Deleting account.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

}
