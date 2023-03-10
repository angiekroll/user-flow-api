package com.neoris.user_flow_api.service;

import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.exception.UserFlowException;

public interface MovementService {

  Movement findById(Long id) throws UserFlowException;

  Movement save(Movement movement) throws UserFlowException;

}
