package com.neoris.user_flow_api.delegate;

import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.time.LocalDate;
import java.util.List;

public interface ReportDelegate {

  List<CustomerMovementDTO> getMovementsByCustomerIdAndDateRange(Long customerId, LocalDate startDate,
      LocalDate endDate) throws UserFlowException;

}
