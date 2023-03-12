package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.util.List;

public interface CustomerService {
  List<Customer> saveAll(List<Customer> customers) throws UserFlowException;
  Customer findById(Long id) throws UserFlowException;
  Customer save(Customer customer) throws UserFlowException;

  void delete(Customer customer) throws UserFlowException;

}
