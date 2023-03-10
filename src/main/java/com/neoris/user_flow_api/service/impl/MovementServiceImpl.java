package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.repository.MovementRepository;
import com.neoris.user_flow_api.service.MovementService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovementServiceImpl implements MovementService {

  private final MovementRepository movementRepository;

  public MovementServiceImpl(MovementRepository movementRepository) {
    this.movementRepository = movementRepository;
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
  public Movement findById(Long id) throws UserFlowException {
    Movement movement = movementRepository.findById(id).orElse(null);

    if (movement == null) {
      log.error("Id no found.");
      throw new UserFlowException(NotificationCode.ID_NOT_FOUND);
    }
    return movement;
  }

}
