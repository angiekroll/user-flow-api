package com.neoris.user_flow_api.controller;

import static org.mockito.Mockito.when;

import com.neoris.user_flow_api.delegate.CustomerDelegate;
import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

  @InjectMocks
  private CustomerController customerController;

  @Mock
  private CustomerDelegate customerDelegate;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void givenListCustomers_whenCreateCustomerWhitValidRequest_thenReturnValidCustomerDTOListCreated()
      throws UserFlowException {
    List<CustomerDTO> customerDTOs = new ArrayList<>();
    customerDTOs.add(new CustomerDTO());

    when(customerDelegate.createCustomer(customerDTOs)).thenReturn(customerDTOs);

    ResponseEntity<List<CustomerDTO>> response = customerController.createCustomer(customerDTOs);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(customerDTOs, response.getBody());

    verify(customerDelegate, times(1)).createCustomer(customerDTOs);
  }

  @Test
  void testUpdateCustomer() throws UserFlowException {
    Long id = 1L;
    CustomerDTO customerDTO = new CustomerDTO();

    when(customerDelegate.updateCustomer(customerDTO, id)).thenReturn(customerDTO);

    ResponseEntity<CustomerDTO> response = customerController.updateCustomer(id, customerDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(customerDTO, response.getBody());

    verify(customerDelegate, times(1)).updateCustomer(customerDTO, id);
  }

  @Test
  void testGetCustomerById() throws UserFlowException {
    Long id = 1L;
    CustomerDTO customerDTO = new CustomerDTO();

    when(customerDelegate.getCustomerById(id)).thenReturn(customerDTO);

    ResponseEntity<CustomerDTO> response = customerController.getCustomerById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(customerDTO, response.getBody());

    verify(customerDelegate, times(1)).getCustomerById(id);
  }

  @Test
  void testDeleteCustomer() throws UserFlowException {
    Long id = 1L;

    doNothing().when(customerDelegate).deleteCustomer(id);

    customerController.deleteCustomer(id);

    verify(customerDelegate, times(1)).deleteCustomer(id);
  }

}
