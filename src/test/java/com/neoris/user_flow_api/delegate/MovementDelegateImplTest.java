package com.neoris.user_flow_api.delegate;

import com.neoris.user_flow_api.constans.AccountType;
import com.neoris.user_flow_api.constans.MovementType;
import com.neoris.user_flow_api.delegate.impl.MovementDelegateImpl;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.service.AccountService;
import com.neoris.user_flow_api.service.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovementDelegateImplTest {

  @Mock
  private AccountService accountService;

  @Mock
  private MovementService movementService;

  @InjectMocks
  private MovementDelegateImpl movementDelegate;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createMovement_shouldCreateCreditMovement() throws UserFlowException {
    MovementDTO movementDTO = new MovementDTO();
    movementDTO.setAmount(BigDecimal.valueOf(100));
    movementDTO.setAccountNumber(123456789L);
    movementDTO.setMovementType(MovementType.CREDIT.name());

    Account account = new Account();
    account.setBalance(BigDecimal.valueOf(100));
    account.setAccountType(AccountType.AHORRO.name());
    account.setState(true);
    account.setAccountNumber(123456789L);
    when(accountService.findByAccountNumberAndStateTrue(movementDTO.getAccountNumber())).thenReturn(
        account);

    Movement expectedMovement = new Movement();
    expectedMovement.setMovementType(MovementType.CREDIT.name());
    expectedMovement.setAmount(new BigDecimal(100));
    expectedMovement.setInitialBalance(account.getBalance());
    expectedMovement.setBalance(account.getBalance().add(movementDTO.getAmount()));
    expectedMovement.setAccount(account);

    when(movementService.save(any(Movement.class))).thenReturn(expectedMovement);

    MovementDTO createdMovementDTO = movementDelegate.createMovement(movementDTO);

    verify(accountService, times(1)).findByAccountNumberAndStateTrue(
        movementDTO.getAccountNumber());
    verify(accountService, times(1)).save(account);
    verify(movementService, times(1)).save(any(Movement.class));
    assertNotNull(createdMovementDTO);
    assertEquals(movementDTO.getAmount(), createdMovementDTO.getAmount());
    assertEquals(movementDTO.getMovementType(), createdMovementDTO.getMovementType());
  }
}