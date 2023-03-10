package com.neoris.user_flow_api.service;


import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.time.LocalDate;
import java.util.List;

public interface AccountService {

  Account findByAccountNumberAndStateTrue(Long accountNumber) throws UserFlowException;

  Account save(Account account) throws UserFlowException;

  Account findById(Long id) throws UserFlowException;

  List<Account> saveAll(List<Account> accounts) throws UserFlowException;

  void delete(Account account) throws UserFlowException;

  List<CustomerMovementDTO> getAccountsByCustomerIdAndDateRange(Long customerId,
      LocalDate startDate, LocalDate endDate)
      throws UserFlowException;

}
