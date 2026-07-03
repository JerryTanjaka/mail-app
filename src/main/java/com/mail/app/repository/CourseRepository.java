package com.mail.app.repository;

import com.mail.app.entity.Course;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, UUID> {}
