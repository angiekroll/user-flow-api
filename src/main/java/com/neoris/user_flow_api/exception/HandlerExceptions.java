package com.neoris.user_flow_api.exception;

import com.neoris.user_flow_api.dto.NotificationDto;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerExceptions {

  @ExceptionHandler(UserFlowException.class)
  public ResponseEntity<Object> handlerCarPoolingException(UserFlowException ex) {
    log.error("Exception: [{}]", ex.getErrorCode().getDescription(), ex);
    return new ResponseEntity<>(
        NotificationDto.builder().message(ex.getErrorCode().getDescription())
            .status(ex.getErrorCode().getHttpStatus().value())
            .error(ex.getErrorCode().getHttpStatus().getReasonPhrase())
            .timestamp(Instant.now())
            .build(),
        ex.getErrorCode().getHttpStatus());
  }

}
