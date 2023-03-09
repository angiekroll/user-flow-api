package com.neoris.user_flow_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Account {

  @Id
  private Long id;
  private String accountNumber;
  private String accountType;
  private BigDecimal initialBalance;
  private boolean state;

}
