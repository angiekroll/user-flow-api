package com.neoris.user_flow_api.constans;

import org.springframework.http.HttpStatus;

public enum NotificationCode {

  ERROR_PROCESSING_DATA("Error processing data.", HttpStatus.INTERNAL_SERVER_ERROR),
  ID_NOT_FOUND("Id no found.", HttpStatus.NOT_FOUND);

  private final HttpStatus httpStatus;
  private final String message;

  NotificationCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getDescription() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
