package com.neoris.user_flow_api.delegate.impl;

import com.neoris.user_flow_api.constans.MovementType;
import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.delegate.MovementDelegate;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.MovementMapper;
import com.neoris.user_flow_api.service.AccountService;
import com.neoris.user_flow_api.service.MovementService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MovementDelegateImpl implements MovementDelegate {

  public final AccountService accountService;
  public final MovementService movementService;

  public MovementDelegateImpl(AccountService accountService, MovementService movementService) {
    this.accountService = accountService;
    this.movementService = movementService;
  }

  @Override
  @Transactional
  public MovementDTO createMovement(MovementDTO movementDTO) throws UserFlowException {
    validateMovementType(movementDTO);
    Account account = accountService.findByAccountNumberAndStateTrue(movementDTO.getAccountNumber());

    BigDecimal balance = new BigDecimal(0);
    if (movementDTO.getMovementType().equalsIgnoreCase(MovementType.DEPOSITO.name())) {
      balance = account.getInitialBalance().add(movementDTO.getAmount());

    }
    if (movementDTO.getMovementType().equalsIgnoreCase(MovementType.RETIRO.name())) {
      balance = account.getInitialBalance().subtract(movementDTO.getAmount());
    }

    Movement movement = MovementMapper.INSTANCE.movementDTOtoMovement(movementDTO);

    movement.setBalance(balance);
    movement.setAccount(account);
    account.setInitialBalance(balance);
    movementService.save(movement);
    accountService.save(account);

    return MovementMapper.INSTANCE.movementToMovementDTO(movement);
  }

  @Override
  public MovementDTO updateMovement(MovementDTO movementDTO, Long id) throws UserFlowException {
    return null;
  }

  @Override
  public MovementDTO getMovementById(Long id) throws UserFlowException {
    return null;
  }

  @Override
  public void deleteMovement(Long id) throws UserFlowException {

  }

  private void validateMovementType(MovementDTO movementDTO) throws UserFlowException {
    if (!movementDTO.getMovementType().equalsIgnoreCase(MovementType.DEPOSITO.name())
        && !movementDTO.getMovementType().equalsIgnoreCase(MovementType.RETIRO.name())) {
      throw new UserFlowException(NotificationCode.INVALID_MOVEMENT_TYPE);
    }
  }
}
