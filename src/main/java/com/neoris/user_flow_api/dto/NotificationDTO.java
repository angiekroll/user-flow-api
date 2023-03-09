package com.neoris.user_flow_api.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO {

  private Instant timestamp;
  private Integer status;
  private String error;
  private String message;

  public NotificationDTO(Instant timestamp, Integer status, String error, String message) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;

  }

}