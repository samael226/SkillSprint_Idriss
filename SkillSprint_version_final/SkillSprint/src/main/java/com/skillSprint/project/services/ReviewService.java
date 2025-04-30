// src/main/java/com/skillSprint/project/services/ReviewService.java
package com.skillSprint.project.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.skillSprint.project.models.Review;
import com.skillSprint.project.repositories.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;

    /**  
     * Return all submissions assigned to this judge that are not yet scored  
     */
    public List<Review> getAssignedReviews(Long judgeId) {
        return reviewRepo.findPendingByJudge(judgeId);
    }

    /**  
     * Submit score & feedback for a given review  
     */
    public Review submitReview(Long reviewId, Integer score, String feedback) {
        Review review = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setScore(score);
        review.setFeedback(feedback);
        review.setReviewedAt(LocalDateTime.now());
        return reviewRepo.save(review);
    }
}
