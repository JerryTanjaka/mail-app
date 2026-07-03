package com.mail.app.endpoint.rest.controller.exception;

import java.util.UUID;

public class AlreadySubscribedException extends RuntimeException {
  public AlreadySubscribedException(UUID userId, UUID courseId) {
    super("User " + userId + " already subscribed to course " + courseId);
  }
}
