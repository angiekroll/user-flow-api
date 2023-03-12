package com.neoris.user_flow_api.utils;

import com.neoris.user_flow_api.constans.AccountType;
import com.neoris.user_flow_api.constans.MovementType;
import com.neoris.user_flow_api.constans.NotificationCode;
import com.neoris.user_flow_api.dto.AccountDTO;
import com.neoris.user_flow_api.dto.MovementDTO;
import com.neoris.user_flow_api.exception.UserFlowException;
import java.math.BigDecimal;

public class ValidationUtils {

  public static void validateMovementType(MovementDTO movementDTO) throws UserFlowException {
    try {
      MovementType.valueOf(movementDTO.getMovementType().toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new UserFlowException(NotificationCode.INVALID_MOVEMENT_TYPE);
    }
  }

  public static void validateValue(BigDecimal amount) throws UserFlowException {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new UserFlowException(NotificationCode.INVALID_AMOUNT);
    }
  }

  public static void validateAccountType(AccountDTO accountDTO) throws UserFlowException {
    try {
      AccountType.valueOf(accountDTO.getAccountType().toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new UserFlowException(NotificationCode.INVALID_MOVEMENT_TYPE);
    }
  }

}
