package com.skillSprint.project.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillSprint.project.dtos.JudgeScoringRequest;
import com.skillSprint.project.models.Submission;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.UserRepository;
import com.skillSprint.project.services.SubmissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final UserRepository userRepo;

    // Submit a challenge solution (User-only)
    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Submission> submitCode(
            @RequestParam Long challengeId,
            @RequestParam String code,
            @RequestParam String language,
            Principal principal) {

        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Submission submission = submissionService.submitCode(user.getUserId(), challengeId, code, language);
        return ResponseEntity.ok(submission);
    }

    // Get all submissions by the logged-in user
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Submission>> getMySubmissions(Principal principal) {
        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Submission> submissions = submissionService.getSubmissionsForUser(user.getUserId());
        return ResponseEntity.ok(submissions);
    }

    // Admin or Judge: Get all submissions for a specific challenge
    @GetMapping("/challenge/{challengeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JUDGE')")
    public ResponseEntity<List<Submission>> getSubmissionsForChallenge(@PathVariable Long challengeId) {
        return ResponseEntity.ok(submissionService.getSubmissionsForChallenge(challengeId));
    }

    // Admin or Judge: View submission by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JUDGE')")
    public ResponseEntity<Submission> getById(@PathVariable Long id) {
        return ResponseEntity.ok(submissionService.getById(id));
    }
    
    @PutMapping("/{id}/score")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Submission> scoreSubmission(@PathVariable Long id,
                                                      @RequestBody JudgeScoringRequest request) {
        return ResponseEntity.ok(submissionService.scoreSubmission(id, request));
    }

}
