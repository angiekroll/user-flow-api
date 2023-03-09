package com.neoris.user_flow_api.mapper;

import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Customer;
import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  @Mapping( source = "customer.id", target = "customerId")
  AccountDTO accountToAccountDTO(Account account);

  @Mapping( source = "customerId", target = "customer.id")
  Account accountDTOToAccount(AccountDTO accountDTO);

  @Mapping(target = "id", ignore = true)
  void updateAccountFromAccountDTO(AccountDTO accountDTO, @MappingTarget Account account);

}
