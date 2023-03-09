package com.neoris.user_flow_api.service;


import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.util.List;

public interface AccountService {

  List<AccountDTO> createAccount(List<AccountDTO> accountsDTO) throws UserFlowException;

  AccountDTO updateAccount(AccountDTO accountDTO, Long id) throws UserFlowException;
  AccountDTO getAccountById(Long id) throws UserFlowException;
  void deleteAccount(Long id) throws UserFlowException;

}
