package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.time.LocalDate;
import java.util.List;

public interface MovementService {

  Movement findById(Long id) throws UserFlowException;

  Movement save(Movement movement) throws UserFlowException;

  List<Object[]> getByCustomerIdAndDateRange(Long customerId, LocalDate startDate,
      LocalDate endDate)
      throws UserFlowException;

}
