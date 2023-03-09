package com.neoris.user_flow_api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDTO {

  private Long accountNumber;

  private String accountType;

  private BigDecimal initialBalance;

  private boolean state;

  private Long customerId;

}
