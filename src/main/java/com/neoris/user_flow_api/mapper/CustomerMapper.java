package com.neoris.user_flow_api.mapper;

import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  CustomerDTO customerToCustomerDTO(Customer customer);

  Customer customerDTOtoCustomer(CustomerDTO customerDTO);

  void updateCustomerFromCustomerDTO(CustomerDTO customerDTO, @MappingTarget Customer customer);



}
