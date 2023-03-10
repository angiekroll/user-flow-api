package com.neoris.user_flow_api.exception;

import com.neoris.user_flow_api.dto.NotificationDTO;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerExceptions {

  @ExceptionHandler(UserFlowException.class)
  public ResponseEntity<Object> handlerUserFlowException(UserFlowException ex) {
    log.error("Exception: [{}]", ex.getErrorCode().getDescription(), ex);
    return new ResponseEntity<>(
        NotificationDTO.builder().message(ex.getErrorCode().getDescription())
            .status(ex.getErrorCode().getHttpStatus().value())
            .error(ex.getErrorCode().getHttpStatus().getReasonPhrase())
            .timestamp(Instant.now())
            .build(),
        ex.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleException(RuntimeException ex) {
    log.error("Fatal error.", ex);
    return new ResponseEntity<>(
        NotificationDTO.builder()
            .message(ex.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(ex.getCause().getMessage())
            .timestamp(Instant.now())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
