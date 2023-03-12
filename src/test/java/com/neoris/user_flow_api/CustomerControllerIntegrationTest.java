package com.neoris.user_flow_api;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoris.user_flow_api.constans.ResourceMapping;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.CustomerDTO;
import com.neoris.user_flow_api.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CustomerRepository customerRepository;

  private Customer customer;

  @BeforeEach
  public void setup() {
    customer = new Customer();
    customer.setName("John");
    customer.setAddress("Otavalo sn y principal");
    customer.setTelephone("098254785");
    customer.setPassword("1234");
    customer.setState(true);
    customerRepository.save(customer);
  }

  @Test
  void givenListCustomers_WhenCreateCustomerWithValidRequest_ThenReturnValidCustomerDTOList() throws Exception {
    List<CustomerDTO> customerDTOs = buildCusomerDTOs();

    mockMvc.perform(post(ResourceMapping.CUSTOMERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customerDTOs)))
        .andExpect(status().isOk());
  }

  @Test
  void givenCustomerId_WhenGetCustomerByIdWithValidId_ThenReturnValidCustomerDTO() throws Exception {
    Long id = 1L;

    mockMvc.perform(get(ResourceMapping.CUSTOMERS + "/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(customer.getName()))
        .andExpect(jsonPath("$.address").value(customer.getAddress()))
        .andExpect(jsonPath("$.telephone").value(customer.getTelephone()))
        .andExpect(jsonPath("$.password").value(customer.getPassword()));
  }

  @Test
  void givenCustomerId_WhenUpdateCustomerWithValidRequest_ThenReturnValidCustomerDTO() throws Exception {

    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setName("UPDATE");
    customerDTO.setAddress("UPDATE");
    customerDTO.setTelephone("123");
    customerDTO.setPassword("123");
    customerDTO.setState(false);

    Long id = 1L;

    mockMvc.perform(put(ResourceMapping.CUSTOMERS + "/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customerDTO)))
        .andExpect(status().isOk());

    Customer customerUpdated = customerRepository.findById(1L).orElse(null);
    assertNotNull(customerUpdated);
    assertEquals(customerDTO.getName(), customerUpdated.getName());
    assertEquals(customerDTO.getAddress(), customerUpdated.getAddress());
    assertEquals(customerDTO.getTelephone(), customerUpdated.getTelephone());
    assertEquals(customerDTO.getPassword(), customerUpdated.getPassword());
    assertEquals(customerDTO.isState(), customerUpdated.isState());

  }

  private List<CustomerDTO> buildCusomerDTOs() {
    List<CustomerDTO> customerDTOs = new ArrayList<>();
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setName("John");
    customerDTO.setAddress("Otavalo sn y principal");
    customerDTO.setTelephone("098254785");
    customerDTO.setPassword("1234");
    customerDTO.setState(true);
    customerDTOs.add(customerDTO);
    return customerDTOs;
  }

}