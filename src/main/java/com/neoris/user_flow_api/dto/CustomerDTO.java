package com.neoris.user_flow_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

  @NotNull
  @NotBlank(message = "The name field is required.")
  @Size(max = 50, message = "The name field cannot be longer than 50 characters.")
  private String name;

  @NotNull
  @NotBlank(message = "The address field is required.")
  @Size(max = 100, message = "The address field cannot be longer than 100 characters.")
  private String address;

  @NotNull
  private String telephone;

  @NotNull(message = "The password field is required.")
  private String password;

  @NotNull
  private boolean state;

  private String gender;

  private int age;

  private Long identification;

//  @JsonIgnore
//  private Long customerId;

}
