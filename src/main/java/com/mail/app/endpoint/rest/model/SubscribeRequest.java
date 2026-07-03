package com.mail.app.endpoint.rest.model;

import java.util.UUID;
import lombok.Data;

@Data
public class SubscribeRequest {
  private UUID courseId;
}
