package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.constans.ResourceMapping;
import com.neoris.user_flow_api.delegate.AccountDelegate;
import com.neoris.user_flow_api.dto.AccountDTO;
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
@RequestMapping(ResourceMapping.ACCOUNTS)
public class AccountController {
  private final AccountDelegate accountDelegate;

  public AccountController(AccountDelegate accountDelegate) {
    this.accountDelegate = accountDelegate;
  }


  @PostMapping
  public ResponseEntity<List<AccountDTO>> createAccount(
      @Valid @RequestBody List<AccountDTO> accountDTOs) throws UserFlowException {

    return ResponseEntity.ok(accountDelegate.createAccount(accountDTOs));
  }

  @PutMapping("/{id}")
  public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id,
      @RequestBody AccountDTO accountDTO) throws UserFlowException {

    return ResponseEntity.ok(accountDelegate.updateAccount(accountDTO, id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) throws UserFlowException {
    return ResponseEntity.ok(accountDelegate.getAccountById(id));
  }

  @DeleteMapping("/{id}")
  public void deleteAccount(@PathVariable Long id) throws UserFlowException {
    accountDelegate.deleteAccount(id);
  }

}
