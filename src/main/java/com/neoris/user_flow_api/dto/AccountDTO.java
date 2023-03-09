package com.neoris.user_flow_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDTO {

  private String accountNumber;
  private String accountType;
  private BigDecimal initialBalance;
  private boolean state;
  private Long customerId;


}
