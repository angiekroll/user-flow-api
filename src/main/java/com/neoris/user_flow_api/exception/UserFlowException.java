package com.neoris.user_flow_api.exception;

import com.neoris.user_flow_api.constans.NotificationCode;
import lombok.Getter;

public class UserFlowException extends Exception {

  @Getter
  private final NotificationCode errorCode;

  public UserFlowException(NotificationCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }

  public UserFlowException(NotificationCode errorCode, Throwable cause) {
    super(errorCode.getDescription(), cause);
    this.errorCode = errorCode;
  }

}
