package com.neoris.user_flow_api.delegate;

import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;

public interface MovementDelegate {
  MovementDTO createMovement(MovementDTO movementDTO) throws UserFlowException;

  MovementDTO getMovementById(Long id) throws UserFlowException;

}
