package com.neoris.user_flow_api.mapper;

import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
  AccountDTO accountToAccountDTO(Account account);

  Account accountDTOToAccount(AccountDTO accountDTO);

  @Mapping(target = "accountNumber", ignore = true)
  void updateAccountFromAccountDTO(AccountDTO accountDTO, @MappingTarget Account account);

}
