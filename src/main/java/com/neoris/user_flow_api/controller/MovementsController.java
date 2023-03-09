package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import com.neoris.user_flow_api.service.MovementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MovementsController {

  private final MovementService movementService;

  public MovementsController(MovementService movementService) {
    this.movementService = movementService;
  }

  @PostMapping
  public ResponseEntity<MovementDTO> createMovement(
      @Valid @RequestBody MovementDTO movementDTO) throws UserFlowException {

    return ResponseEntity.ok(movementService.createMovement(movementDTO));

  }

  @PutMapping("/{id}")
  public ResponseEntity<MovementDTO> updateAccount(@PathVariable Long id,
      @RequestBody MovementDTO movementDTO) throws UserFlowException {

    return ResponseEntity.ok(movementService.updateMovement(movementDTO, id));

  }

  @GetMapping("/{id}")
  public ResponseEntity<MovementDTO> getAccountById(@PathVariable Long id) throws UserFlowException {
    return ResponseEntity.ok(movementService.getMovementByAccountNumber(id));
  }

  @DeleteMapping("/{id}")
  public void deleteAccount(@PathVariable Long id) throws UserFlowException {
    movementService.deleteMovement(id);
  }

}
