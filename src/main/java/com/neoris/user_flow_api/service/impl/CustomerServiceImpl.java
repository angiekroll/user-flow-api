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

    Customer customer = getCustomerById(id);
    customer = customerRepository.save(validateFieldsToUpdate(customerDTO, customer));
    log.info("{} customers updated", id);

    return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
  }

  @Override
  public Customer getCustomerById(Long id) throws UserFlowException {
    Customer customer = customerRepository.findById(id).orElse(null);

    if (customer == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ID_NOT_FOUND);
    }
    return customer;
  }

  @Override
  public void deleteById(Long id) throws UserFlowException {
    getCustomerById(id);
    customerRepository.deleteById(id);
  }

  private Customer validateFieldsToUpdate(CustomerDTO customerDTO, Customer customer) {
    if (customerDTO.getName() != null) {
      customer.setName(customerDTO.getName());
    }
    if (customerDTO.getPassword() != null) {
      customer.setPassword(customerDTO.getPassword());
    }
    if (customerDTO.getAddress() != null) {
      customer.setAddress(customerDTO.getAddress());
    }
    if (customerDTO.getTelephone() != null) {
      customer.setTelephone(customerDTO.getTelephone());
    }
    if (customerDTO.getGender() != null) {
      customer.setGender(customerDTO.getGender());
    }
    if (customerDTO.getAge() != customer.getAge()) {
      customer.setAge(customerDTO.getAge());
    }
    customer.setState(customerDTO.isState());

    return customer;
  }

}
