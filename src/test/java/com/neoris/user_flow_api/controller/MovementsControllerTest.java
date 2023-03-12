package com.neoris.user_flow_api.controller;

import com.neoris.user_flow_api.delegate.MovementDelegate;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovementsControllerTest {

  @Mock
  private MovementDelegate movementDelegateMock;

  private MovementsController movementsController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    movementsController = new MovementsController(movementDelegateMock);
  }

  @Test
  void testCreateMovement() throws UserFlowException {
    MovementDTO movementDTO = new MovementDTO();

    when(movementDelegateMock.createMovement(any(MovementDTO.class))).thenReturn(movementDTO);

    ResponseEntity<MovementDTO> response = movementsController.createMovement(movementDTO);

    assert response.getStatusCode() == HttpStatus.OK;
    assert response.getBody() == movementDTO;
  }

  @Test
  void testGetMovementById() throws UserFlowException {
    MovementDTO movementDTO = new MovementDTO();
    Long movementId = 1L;

    when(movementDelegateMock.getMovementById(movementId)).thenReturn(movementDTO);

    ResponseEntity<MovementDTO> response = movementsController.getMovementById(movementId);

    assert response.getStatusCode() == HttpStatus.OK;
    assert response.getBody() == movementDTO;
  }
}