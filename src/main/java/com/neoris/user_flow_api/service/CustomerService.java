package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.util.List;

public interface CustomerService {
  List<CustomerDTO> createCustomer(List<CustomerDTO> customersDTO) throws UserFlowException;

  CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) throws UserFlowException;
  CustomerDTO getCustomerById(Long id) throws UserFlowException;
  void deleteCustomer(Long id) throws UserFlowException;

}
