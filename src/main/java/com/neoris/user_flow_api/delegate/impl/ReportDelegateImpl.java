package com.neoris.user_flow_api.delegate.impl;

import com.neoris.user_flow_api.delegate.ReportDelegate;
import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.service.AccountService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportDelegateImpl implements ReportDelegate {

  private final AccountService accountService;

  public ReportDelegateImpl(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public List<CustomerMovementDTO> getMovementsByCustomerIdAndDateRange(
      Long customerId,
      LocalDate startDate,
      LocalDate endDate) throws UserFlowException {

    return accountService.getAccountsByCustomerIdAndDateRange(customerId, startDate, endDate);

  }
}
