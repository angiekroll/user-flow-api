package com.neoris.user_flow_api.service.impl;

import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.service.MovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovementServiceImpl implements MovementService {

  @Override
  public MovementDTO createMovement(MovementDTO movementDTO) throws UserFlowException {
    return null;
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
