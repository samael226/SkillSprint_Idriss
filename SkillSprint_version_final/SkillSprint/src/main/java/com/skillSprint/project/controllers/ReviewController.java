// src/main/java/com/skillSprint/project/controllers/ReviewController.java
package com.skillSprint.project.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.skillSprint.project.models.Review;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.UserRepository;
import com.skillSprint.project.services.ReviewService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepo;

    /**  
     * GET /api/reviews/assigned  
     * Returns all reviews (submissions) assigned to the logged-in judge that are pending scoring  
     */
    @GetMapping("/assigned")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<List<Review>> getAssigned(Principal principal) {
        User judge = userRepo.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(reviewService.getAssignedReviews(judge.getUserId()));
    }

    /**  
     * PUT /api/reviews/{id}  
     * Judge submits score & feedback  
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Review> submitReview(
            @PathVariable Long id,
            @RequestBody ReviewRequest req) {
        Review updated = reviewService.submitReview(id, req.getScore(), req.getFeedback());
        return ResponseEntity.ok(updated);
    }

    @Data
    static class ReviewRequest {
        private Integer score;
        private String feedback;
    }
}
