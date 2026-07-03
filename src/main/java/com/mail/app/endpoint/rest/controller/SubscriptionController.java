package com.mail.app.endpoint.rest.controller;

import com.mail.app.endpoint.event.EventProducer;
import com.mail.app.endpoint.event.model.SubscriptionConfirmationRequested;
import com.mail.app.endpoint.rest.model.SubscribeRequest;
import com.mail.app.endpoint.rest.model.SubscriptionResponse;
import com.mail.app.service.SubscriptionService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}/subscriptions")
@AllArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;
  private final EventProducer<SubscriptionConfirmationRequested> eventProducer;

  @PostMapping
  public ResponseEntity<SubscriptionResponse> subscribe(
      @PathVariable UUID userId, @RequestBody SubscribeRequest request) {

    UUID subscriptionId = subscriptionService.subscribe(userId, request.getCourseId());

    eventProducer.accept(
        List.of(
            SubscriptionConfirmationRequested.builder()
                .userId(userId)
                .courseId(request.getCourseId())
                .build()));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new SubscriptionResponse(subscriptionId, userId, request.getCourseId()));
  }
}
