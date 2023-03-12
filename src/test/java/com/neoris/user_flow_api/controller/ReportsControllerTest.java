package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.delegate.ReportDelegate;
import com.neoris.user_flow_api.dto.CustomerMovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportsControllerTest {

  private ReportsController reportsController;

  @Mock
  private ReportDelegate reportDelegate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    reportsController = new ReportsController(reportDelegate);
  }

  @Test
  void getMovementsByCustomerIdAndDateRange_shouldReturnOk() throws UserFlowException {
    Long customerId = 1L;
    LocalDate startDate = LocalDate.of(2022, 1, 1);
    LocalDate endDate = LocalDate.of(2022, 1, 31);

    List<CustomerMovementDTO> customerMovementDTOs = Collections.singletonList(CustomerMovementDTO.builder()
        .build());

    when(reportDelegate.getMovementsByCustomerIdAndDateRange(customerId, startDate, endDate))
        .thenReturn(customerMovementDTOs);

    ResponseEntity<List<CustomerMovementDTO>> responseEntity = reportsController.getMovementsByCustomerIdAndDateRange(customerId, startDate, endDate);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(customerMovementDTOs, responseEntity.getBody());
    verify(reportDelegate).getMovementsByCustomerIdAndDateRange(customerId, startDate, endDate);
  }

  @Test
  void getMovementsByCustomerIdAndDateRange_shouldReturnEmptyList() throws UserFlowException {
    Long customerId = 1L;
    LocalDate startDate = LocalDate.of(2022, 1, 1);
    LocalDate endDate = LocalDate.of(2022, 1, 31);

    when(reportDelegate.getMovementsByCustomerIdAndDateRange(customerId, startDate, endDate))
        .thenReturn(Collections.emptyList());

    ResponseEntity<List<CustomerMovementDTO>> responseEntity = reportsController.getMovementsByCustomerIdAndDateRange(customerId, startDate, endDate);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(Collections.emptyList(), responseEntity.getBody());
    verify(reportDelegate).getMovementsByCustomerIdAndDateRange(customerId, startDate, endDate);
  }
}