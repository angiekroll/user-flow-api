package com.neoris.user_flow_api.constans;

import org.springframework.http.HttpStatus;

public enum NotificationCode {

  ERROR_PROCESSING_DATA("Error processing data.", HttpStatus.INTERNAL_SERVER_ERROR),
  ID_NOT_FOUND("Id no found.", HttpStatus.NOT_FOUND),
  CUSTOMER_NOT_FOUND("Customer not found.", HttpStatus.NOT_FOUND),
  INVALID_ACCOUNT_TYPE("Invalid account type", HttpStatus.BAD_REQUEST),
  INVALID_MOVEMENT_TYPE("Invalid movement type", HttpStatus.BAD_REQUEST),
  ACCOUNT_NOT_FOUND("Account not found.", HttpStatus.NOT_FOUND),
  ERROR_SAVING_DATA("Error saving data.", HttpStatus.INTERNAL_SERVER_ERROR),
  BALANCE_NOT_AVAILABLE("Balance not available.", HttpStatus.BAD_REQUEST),
  INVALID_AMOUNT("Invalid amount.", HttpStatus.BAD_REQUEST),
  ERROR_GETTING_DATA("Error getting data.", HttpStatus.INTERNAL_SERVER_ERROR),
  MISSING_REQUIRED_PARAMS("Missing required parameters", HttpStatus.BAD_REQUEST);


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
