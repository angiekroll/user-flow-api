package com.neoris.user_flow_api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
  private Long accountNumber;
  private String accountType;
  private BigDecimal balance;
  private boolean state;
  @ManyToOne
  @JoinColumn(name = "customerId")
  private Customer customer;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Movement> movements = new ArrayList<>();

}
