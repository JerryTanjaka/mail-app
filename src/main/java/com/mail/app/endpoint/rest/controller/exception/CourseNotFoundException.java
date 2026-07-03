package com.mail.app.endpoint.rest.controller.exception;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
  public CourseNotFoundException(UUID courseId) {
    super("Course not found: " + courseId);
  }
}
