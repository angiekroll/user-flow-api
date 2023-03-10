package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.repository.AccountRepository;
import com.neoris.user_flow_api.repository.CustomerRepository;
import com.neoris.user_flow_api.service.AccountService;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
      log.info("Deleting account");
      accountRepository.delete(account);
    } catch (Exception e) {
      log.error("Error Deleting account.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  public List<CustomerMovementDTO> getAccountsByCustomerIdAndDateRange(Long customerId,
      LocalDate startDate,
      LocalDate endDate)
      throws UserFlowException {
    try {
      log.info("Getting data");
      List<Object[]> result = accountRepository.findByCustomerIdAndDataRange(customerId, startDate,
          endDate);
      List<CustomerMovementDTO> customerMovementDTOs = new ArrayList<>();

      for (Object[] row : result) {
        CustomerMovementDTO customerMovementDTO = new CustomerMovementDTO();
        customerMovementDTO.setDate(((Date) row[0]).toLocalDate());
        customerMovementDTO.setCustomer(String.valueOf(row[1]));
        customerMovementDTO.setAccountNumber((Long) row[2]);
        customerMovementDTO.setAccountType((String) row[3]);
        customerMovementDTO.setInitialBalance((BigDecimal) row[4]);
        customerMovementDTO.setState((Boolean) row[5]);
        customerMovementDTO.setAmount((BigDecimal) row[6]);
        customerMovementDTO.setBalance((BigDecimal) row[7]);

        customerMovementDTOs.add(customerMovementDTO);
      }

      return customerMovementDTOs;

    } catch (Exception e) {
      log.error("Error getting the data.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

}
