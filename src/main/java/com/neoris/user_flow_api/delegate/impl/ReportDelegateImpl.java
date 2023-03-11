package com.neoris.user_flow_api.delegate.impl;

import com.neoris.user_flow_api.delegate.ReportDelegate;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.service.CustomerService;
import com.neoris.user_flow_api.service.MovementService;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportDelegateImpl implements ReportDelegate {

  private final MovementService movementService;
  private final CustomerService customerService;

  public ReportDelegateImpl(MovementService movementService, CustomerService customerService) {
    this.movementService = movementService;
    this.customerService = customerService;
  }

  @Override
  public List<CustomerMovementDTO> getMovementsByCustomerIdAndDateRange(
      Long customerId,
      LocalDate startDate,
      LocalDate endDate) throws UserFlowException {
    Customer customer = customerService.findById(customerId);

    List<Object[]> result = movementService.getByCustomerIdAndDateRange(customerId,
        startDate, endDate);
    List<CustomerMovementDTO> customerMovementDTOs = new ArrayList<>();

    for (Object[] row : result) {
      CustomerMovementDTO customerMovementDTO = new CustomerMovementDTO();
      customerMovementDTO.setDate(((Date) row[0]).toLocalDate());
      customerMovementDTO.setCustomer(customer.getName());
      customerMovementDTO.setAccountNumber((Long) row[2]);
      customerMovementDTO.setAccountType((String) row[3]);
      customerMovementDTO.setInitialBalance((BigDecimal) row[4]);
      customerMovementDTO.setState((Boolean) row[5]);
      customerMovementDTO.setAmount((BigDecimal) row[6]);
      customerMovementDTO.setBalance((BigDecimal) row[7]);

      customerMovementDTOs.add(customerMovementDTO);
    }

    return customerMovementDTOs;
  }

}
