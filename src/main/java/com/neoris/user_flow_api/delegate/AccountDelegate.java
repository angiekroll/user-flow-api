package com.neoris.user_flow_api.delegate;

import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.util.List;

public interface AccountDelegate {
  List<AccountDTO> createAccount(List<AccountDTO> accountDTOs) throws UserFlowException;

  AccountDTO updateAccount(AccountDTO accountDTO, Long id) throws UserFlowException;

  AccountDTO getAccountById(Long accountNumber) throws UserFlowException;

  void deleteAccount(Long id) throws UserFlowException;

}
