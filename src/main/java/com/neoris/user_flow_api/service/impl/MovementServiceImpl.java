package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.MovementType;
import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Account;
import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.mapper.MovementMapper;
import com.neoris.user_flow_api.repository.AccountRepository;
import com.neoris.user_flow_api.repository.MovementRepository;
import com.neoris.user_flow_api.service.MovementService;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MovementServiceImpl implements MovementService {

  private final MovementRepository movementRepository;
  private final AccountRepository accountRepository;

  public MovementServiceImpl(MovementRepository movementRepository,
      AccountRepository accountRepository) {
    this.movementRepository = movementRepository;
    this.accountRepository = accountRepository;
  }

  @Transactional
  @Override
  public MovementDTO createMovement(MovementDTO movementDTO) throws UserFlowException {

    if (!movementDTO.getMovementType().equalsIgnoreCase(MovementType.DEPOSITO.name())
        && !movementDTO.getMovementType().equalsIgnoreCase(MovementType.RETIRO.name())) {
      throw new UserFlowException(NotificationCode.INVALID_MOVEMENT_TYPE);
    }

    Account account = accountRepository.findByAccountNumberAndStateTrue(
        movementDTO.getAccountNumber()).orElse(null);
    if (account == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ACCOUNT_NOT_FOUND);
    }

    Movement movement = MovementMapper.INSTANCE.movementDTOtoMovement(movementDTO);

    BigDecimal balance = new BigDecimal(0);
    if (movement.getMovementType().equalsIgnoreCase(MovementType.DEPOSITO.name())) {
      balance = account.getInitialBalance().add(movement.getAmount());

    }
    if (movement.getMovementType().equalsIgnoreCase(MovementType.RETIRO.name())) {
      balance = account.getInitialBalance().subtract(movement.getAmount());
    }
    movement.setBalance(balance);
    movement.setDate(LocalDate.now());
    movement.setAccount(account);
    account.setInitialBalance(balance);
    movementRepository.save(movement);
    accountRepository.save(account);

    return MovementMapper.INSTANCE.movementToMovementDTO(movement);
  }

  @Override
  public MovementDTO updateMovement(MovementDTO movementDTO, Long id) throws UserFlowException {
    return null;
  }

  @Override
  public MovementDTO getMovementByAccountNumber(Long id) throws UserFlowException {
    return null;
  }

  @Override
  public void deleteMovement(Long id) throws UserFlowException {

  }
}
