package com.mail.app.service;

import com.mail.app.endpoint.rest.exception.AlreadySubscribedException;
import com.mail.app.endpoint.rest.exception.CourseNotFoundException;
import com.mail.app.endpoint.rest.exception.UserNotFoundException;
import com.mail.app.entity.Subscription;
import com.mail.app.repository.CourseRepository;
import com.mail.app.repository.SubscriptionRepository;
import com.mail.app.repository.UserRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public UUID subscribe(UUID userId, UUID courseId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        var course =
                courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if (subscriptionRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new AlreadySubscribedException(userId, courseId);
        }

        var subscription = new Subscription(UUID.randomUUID(), user, course, Instant.now());
        subscriptionRepository.save(subscription);
        return subscription.getId();
    }
}