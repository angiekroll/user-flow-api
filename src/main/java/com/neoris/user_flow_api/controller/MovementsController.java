package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.constans.ResourceMapping;
import com.neoris.user_flow_api.delegate.MovementDelegate;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourceMapping.MOVEMENTS)
public class MovementsController {

  private final MovementDelegate movementDelegate;

  public MovementsController(MovementDelegate movementDelegate) {
    this.movementDelegate = movementDelegate;
  }

  @PostMapping
  public ResponseEntity<MovementDTO> createMovement(
      @Valid @RequestBody MovementDTO movementDTO) throws UserFlowException {

    return ResponseEntity.ok(movementDelegate.createMovement(movementDTO));

  }

  @GetMapping("/{id}")
  public ResponseEntity<MovementDTO> getMovementById(@PathVariable Long id)
      throws UserFlowException {
    return ResponseEntity.ok(movementDelegate.getMovementById(id));
  }

}
