package com.mail.app.endpoint.rest.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionResponse {
  private UUID id;
  private UUID userId;
  private UUID courseId;
}
