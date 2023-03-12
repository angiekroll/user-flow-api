package com.neoris.user_flow_api.delegate;

import static org.mockito.Mockito.when;

import com.neoris.user_flow_api.delegate.impl.CustomerDelegateImpl;
import com.neoris.user_flow_api.domain.Customer;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.service.CustomerService;

class CustomerDelegateImplTest {

  @InjectMocks
  CustomerDelegateImpl customerDelegate;

  @Mock
  CustomerService customerService;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateCustomer() throws UserFlowException {
    List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setName("John");
    customerDTOs.add(customerDTO);
    when(customerService.saveAll(Mockito.anyList())).thenReturn(new ArrayList<Customer>());
    List<CustomerDTO> savedCustomers = customerDelegate.createCustomer(customerDTOs);
    Assertions.assertNotNull(savedCustomers);
  }

  @Test
  void testUpdateCustomer() throws UserFlowException {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setName("John");
    when(customerService.findById(Mockito.anyLong())).thenReturn(new Customer());
    when(customerService.save(Mockito.any(Customer.class))).thenReturn(new Customer());
    CustomerDTO updatedCustomer = customerDelegate.updateCustomer(customerDTO, 1L);
    Assertions.assertNotNull(updatedCustomer);
  }

  @Test
  void testGetCustomerById() throws UserFlowException {
    when(customerService.findById(Mockito.anyLong())).thenReturn(new Customer());
    CustomerDTO customerDTO = customerDelegate.getCustomerById(1L);
    Assertions.assertNotNull(customerDTO);
  }

  @Test
  void testDeleteCustomer() throws UserFlowException {
    when(customerService.findById(Mockito.anyLong())).thenReturn(new Customer());
    customerDelegate.deleteCustomer(1L);
  }

}