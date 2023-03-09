package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.CustomerMapper;
import com.neoris.user_flow_api.repository.CustomerRepository;
import com.neoris.user_flow_api.service.CustomerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  @Transactional
  public List<CustomerDTO> createCustomer(List<CustomerDTO> customersDTO) throws UserFlowException {
    try {
      List<Customer> customers = customersDTO.stream()
          .map(CustomerMapper.INSTANCE::customerDTOtoCustomer)
          .toList();

      log.debug("Saving customers");
      List<Customer> savedCustomers = customerRepository.saveAll(customers);
      log.info("{} customers created", savedCustomers.size());

      return savedCustomers.stream()
          .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
          .toList();

    } catch (Exception e) {
      log.error("An error occurred while saving the data.", e);
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  @Override
  @Transactional
  public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) throws UserFlowException {
    Customer customerFound = findById(id);
    CustomerMapper.INSTANCE.updateCustomerFromCustomerDTO(customerDTO, customerFound);
    try {
      Customer updatedCustomer = customerRepository.save(customerFound);
      log.info("{} Customer updated", id);
      return CustomerMapper.INSTANCE.customerToCustomerDTO(updatedCustomer);
    } catch (Exception e) {
      log.debug("{} Error updating customer", id);
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA);
    }
  }

  @Override
  public CustomerDTO getCustomerById(Long id) throws UserFlowException {
    return CustomerMapper.INSTANCE.customerToCustomerDTO(findById(id));
  }

  @Override
  public void deleteCustomer(Long id) throws UserFlowException {
    Customer customer = findById(id);
    customerRepository.delete(customer);
    log.info("{} Customer deleted", id);
  }

  public Customer findById(Long id) throws UserFlowException {
    Customer customer = customerRepository.findById(id).orElse(null);

    if (customer == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ID_NOT_FOUND);
    }
    return customer;
  }

}
