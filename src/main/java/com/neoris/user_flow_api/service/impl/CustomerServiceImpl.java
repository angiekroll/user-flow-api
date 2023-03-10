package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.repository.CustomerRepository;
import com.neoris.user_flow_api.service.CustomerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer findById(Long id) throws UserFlowException {
    Customer customer = customerRepository.findById(id).orElse(null);

    if (customer == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ID_NOT_FOUND);
    }
    return customer;
  }

  @Override
  public Customer save(Customer customer) throws UserFlowException {
    try {
      log.info("Saving customer");
      return customerRepository.save(customer);
    } catch (Exception e) {
      log.error("Error Saving customer.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }

  }

  public List<Customer> saveAll(List<Customer> customers) throws UserFlowException {
    try {
      log.info("Saving customers");
      return customerRepository.saveAll(customers);
    } catch (Exception e) {
      log.error("Error Saving customers.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  @Override
  public void delete(Customer customer) throws UserFlowException {
    try {
      log.info("Deleting customer");
      customerRepository.delete(customer);
    } catch (Exception e) {
      log.error("Error Deleting customer.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

}
