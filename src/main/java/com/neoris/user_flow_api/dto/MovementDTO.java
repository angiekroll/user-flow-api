package com.neoris.user_flow_api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MovementDTO {

  private Long accountNumber;

  private String movementType;

  private BigDecimal amount;

}
