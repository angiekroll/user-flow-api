package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.constans.ResourceMapping;
import com.neoris.user_flow_api.delegate.ReportDelegate;
import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourceMapping.REPORTS)
public class ReportsController {

  private final ReportDelegate reportDelegate;

  public ReportsController(ReportDelegate reportDelegate) {
    this.reportDelegate = reportDelegate;
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<List<CustomerMovementDTO>> getMovementsByCustomerIdAndDateRange(
      @PathVariable Long customerId,
      @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
  )
      throws UserFlowException {
    List<CustomerMovementDTO> customerMovementDTOs = reportDelegate.getMovementsByCustomerIdAndDateRange(
        customerId, startDate, endDate);

    return ResponseEntity.ok(customerMovementDTOs);
  }

}
