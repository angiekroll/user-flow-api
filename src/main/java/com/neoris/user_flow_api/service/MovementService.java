package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;

public interface MovementService {
  MovementDTO update(MovementDTO movementDTO, Long id) throws UserFlowException;
  MovementDTO getMovementById(Long id) throws UserFlowException;
  void delete(Long id) throws UserFlowException;

  Movement save(Movement movement) throws UserFlowException;

}
