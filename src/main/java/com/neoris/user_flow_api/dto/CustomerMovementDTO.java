package com.neoris.user_flow_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CustomerMovementDTO {

  private LocalDate date;
  private String customer;
  private Long accountNumber;
  private String accountType;
  private BigDecimal initialBalance;
  private boolean state;
  private BigDecimal amount;
  private BigDecimal balance;

}
