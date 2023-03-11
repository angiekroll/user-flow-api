package com.neoris.user_flow_api.delegate.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.delegate.CustomerDelegate;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.CustomerMapper;
import com.neoris.user_flow_api.service.CustomerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomerDelegateImpl implements CustomerDelegate {

  public final CustomerService customerService;

  public CustomerDelegateImpl(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  @Transactional
  public List<CustomerDTO> createCustomer(List<CustomerDTO> customerDTOs) throws UserFlowException {

    List<Customer> customers = customerDTOs.stream()
        .map(CustomerMapper.INSTANCE::customerDTOtoCustomer)
        .toList();

    List<Customer> savedCustomers = customerService.saveAll(customers);
    log.info("{} customers created", savedCustomers.size());

    return savedCustomers.stream()
        .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
        .toList();
  }

  @Override
  @Transactional
  public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) throws UserFlowException {
    Customer customerFound = customerService.findById(id);
    CustomerMapper.INSTANCE.updateCustomerFromCustomerDTO(customerDTO, customerFound);
    try {
      Customer updatedCustomer = customerService.save(customerFound);
      log.info("{} Customer updated", id);
      return CustomerMapper.INSTANCE.customerToCustomerDTO(updatedCustomer);
    } catch (Exception e) {
      log.debug("{} Error updating customer", id);
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA);
    }
  }

  @Override
  public CustomerDTO getCustomerById(Long id) throws UserFlowException {
    Customer customer = customerService.findById(id);
    return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
  }

  @Override
  public void deleteCustomer(Long id) throws UserFlowException {
    Customer customer = customerService.findById(id);
    customerService.delete(customer);
    log.info("{} Customer deleted", id);
  }

}
