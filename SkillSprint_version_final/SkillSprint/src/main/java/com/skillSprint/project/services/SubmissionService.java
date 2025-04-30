package com.skillSprint.project.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.skillSprint.project.dtos.JudgeScoringRequest;
import com.skillSprint.project.models.Challenge;
import com.skillSprint.project.models.Submission;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.ChallengeRepository;
import com.skillSprint.project.repositories.SubmissionRepository;
import com.skillSprint.project.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepo;
    private final UserRepository userRepo;
    private final ChallengeRepository challengeRepo;
    private final GamificationService gamificationService;

    public Submission submitCode(Long userId, Long challengeId, String code, String language) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Challenge challenge = challengeRepo.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        Submission submission = Submission.builder()
                .user(user)
                .challenge(challenge)
                .code(code)
                .language(language)
                .status("PENDING")
                .submittedAt(LocalDateTime.now())
                .build();

        // Here you can integrate an AI evaluator or run tests
        evaluateSubmission(submission);

        Submission saved = submissionRepo.save(submission);

        // Award XP if passed
        if ("PASSED".equalsIgnoreCase(saved.getStatus())) {
            gamificationService.awardXp(
                    user,
                    challenge.getXpReward(), // XP reward from challenge
                    "Completed challenge: " + challenge.getTitle()
            );
        }

        return saved;
    }

    private void evaluateSubmission(Submission submission) {
        // Simple mock logic (replace with actual AI or judge logic)
        if (submission.getCode() != null && submission.getCode().length() > 20) {
            submission.setStatus("PASSED");
            submission.setScore(100);
            submission.setFeedback("Great job! 🎉");
        } else {
            submission.setStatus("FAILED");
            submission.setScore(0);
            submission.setFeedback("Incomplete or incorrect solution.");
        }
    }

    public List<Submission> getSubmissionsForUser(Long userId) {
        return submissionRepo.findByUser_UserId(userId);
    }

    public List<Submission> getSubmissionsForChallenge(Long challengeId) {
        return submissionRepo.findByChallengeId(challengeId);
    }

    public Submission getById(Long id) {
        return submissionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }
    
    
    public Submission scoreSubmission(Long submissionId, JudgeScoringRequest request) {
        Submission submission = submissionRepo.findById(submissionId)
            .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setScore(request.getScore().intValue());
        submission.setJudgeFeedback(request.getFeedback());
        submission.setJudgedAt(new Date());
        submission.setStatus("Scored");

        return submissionRepo.save(submission);
    }

}
