package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.repository.AccountRepository;
import com.neoris.user_flow_api.repository.MovementRepository;
import com.neoris.user_flow_api.service.MovementService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


  @Override
  public Movement save(Movement movement) throws UserFlowException {
    movement.setDate(LocalDate.now());
    try {
      log.info("Saving movement");
      return movementRepository.save(movement);
    } catch (Exception e) {
      log.error("Error Saving movement.");
      throw new UserFlowException(NotificationCode.ERROR_PROCESSING_DATA, e);
    }
  }

  @Override
  public MovementDTO update(MovementDTO movementDTO, Long id) throws UserFlowException {
    return null;
  }

  @Override
  public MovementDTO getMovementById(Long id) throws UserFlowException {
    return null;
  }

  @Override
  public void delete(Long id) throws UserFlowException {

  }


}
