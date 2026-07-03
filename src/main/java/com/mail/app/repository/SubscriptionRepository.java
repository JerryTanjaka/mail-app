package com.mail.app.repository;

import com.mail.app.entity.Subscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
  boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
