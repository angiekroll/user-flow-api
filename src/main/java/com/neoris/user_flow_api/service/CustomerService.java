package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.util.List;

public interface CustomerService {
  List<CustomerDTO> createCustomer(List<CustomerDTO> customersDTO) throws UserFlowException;

  CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) throws UserFlowException;
  Customer getCustomerById(Long id) throws UserFlowException;
  void deleteById(Long id) throws UserFlowException;

}
