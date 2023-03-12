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
import com.neoris.user_flow_api.utils.ValidationUtils;
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
    ValidationUtils.validateMovementType(movementDTO);

    if (movementDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new UserFlowException(NotificationCode.INVALID_AMOUNT);
    }

    Account account = accountService.findByAccountNumberAndStateTrue(movementDTO.getAccountNumber());

    BigDecimal balance = movementDTO.getMovementType().equalsIgnoreCase(MovementType.DEBIT.name())
        ? account.getBalance().subtract(movementDTO.getAmount())
        : account.getBalance().add(movementDTO.getAmount());

    if (balance.compareTo(BigDecimal.ZERO) < 0 && movementDTO.getMovementType()
        .equalsIgnoreCase(MovementType.DEBIT.name())) {
      throw new UserFlowException(NotificationCode.BALANCE_NOT_AVAILABLE);
    }

    Movement movement = MovementMapper.INSTANCE.movementDTOtoMovement(movementDTO);

    movement.setInitialBalance(account.getBalance());
    account.setBalance(balance);
    movement.setAccount(account);
    movement.setBalance(balance);
    accountService.save(account);
    movementService.save(movement);

    return MovementMapper.INSTANCE.movementToMovementDTO(movement);
  }

  @Override
  public MovementDTO getMovementById(Long id) throws UserFlowException {
    Movement movement = movementService.findById(id);
    return MovementMapper.INSTANCE.movementToMovementDTO(movement);
  }

}
