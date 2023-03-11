package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.constans.ResourceMapping;
import com.neoris.user_flow_api.delegate.CustomerDelegate;
import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourceMapping.CUSTOMERS)
public class CustomerController {
  private final CustomerDelegate customerDelegate;

  public CustomerController(CustomerDelegate customerDelegate) {
    this.customerDelegate = customerDelegate;
  }


  @PostMapping
  public ResponseEntity<List<CustomerDTO>> createCustomer(
      @Valid @RequestBody List<CustomerDTO> customerDTOs) throws UserFlowException {

    return ResponseEntity.ok(customerDelegate.createCustomer(customerDTOs));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
      @RequestBody CustomerDTO customersDTO) throws UserFlowException {

    return ResponseEntity.ok(customerDelegate.updateCustomer(customersDTO, id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) throws UserFlowException {
    return ResponseEntity.ok(customerDelegate.getCustomerById(id));
  }

  @DeleteMapping("/{id}")
  public void deleteCustomer(@PathVariable Long id) throws UserFlowException {
    customerDelegate.deleteCustomer(id);
  }

}
