package com.skillSprint.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skillSprint.project.models.AiReview;

public interface AiReviewRepository extends JpaRepository<AiReview, Long> {
}
