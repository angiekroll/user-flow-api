package com.neoris.user_flow_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.neoris.user_flow_api.delegate.AccountDelegate;
import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.exception.UserFlowException;

class AccountControllerTest {

  @Mock
  private AccountDelegate accountDelegate;

  @InjectMocks
  private AccountController accountController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void givenAccount_whenCreateAccountWhitValidRequest_thenReturnValidAccountCreated() throws UserFlowException {
    List<AccountDTO> accountDTOs = new ArrayList<>();
    AccountDTO accountDTO = new AccountDTO();
    accountDTOs.add(accountDTO);

    when(accountDelegate.createAccount(anyList())).thenReturn(accountDTOs);

    ResponseEntity<List<AccountDTO>> response = accountController.createAccount(accountDTOs);

    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(accountDTOs);
  }

  @Test
  void givenAccount_whenUpdateAccountWhitValidRequest_thenReturnValidAccountUpdated() throws UserFlowException {
    Long id = 1L;
    AccountDTO accountDTO = new AccountDTO();

    when(accountDelegate.updateAccount(any(AccountDTO.class), anyLong())).thenReturn(accountDTO);

    ResponseEntity<AccountDTO> response = accountController.updateAccount(id, accountDTO);

    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(accountDTO);
  }

  @Test
  void givenAccount_whenGetAccountById_thenReturnValidAccount() throws UserFlowException {
    Long id = 1L;
    AccountDTO accountDTO = new AccountDTO();

    when(accountDelegate.getAccountById(anyLong())).thenReturn(accountDTO);

    ResponseEntity<AccountDTO> response = accountController.getAccountById(id);

    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(accountDTO);
  }

  @Test
  void givenAccount_whenDeleteAccountById_thenAccountDeleted() throws UserFlowException {
    Long id = 1L;

    accountController.deleteAccount(id);
  }
}