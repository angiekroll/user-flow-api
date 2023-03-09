package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;

public interface MovementService {

  MovementDTO createMovement(MovementDTO movementDTO) throws UserFlowException;
  MovementDTO updateMovement(MovementDTO movementDTO, Long id) throws UserFlowException;
  MovementDTO getMovementByAccountNumber(Long id) throws UserFlowException;
  void deleteMovement(Long id) throws UserFlowException;

}
