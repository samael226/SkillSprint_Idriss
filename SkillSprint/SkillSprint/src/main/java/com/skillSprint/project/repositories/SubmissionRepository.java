package com.skillSprint.project.repositories;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skillSprint.project.dtos.LeaderboardItem;
import com.skillSprint.project.models.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser_UserId(Long userId);
    List<Submission> findByChallengeId(Long challengeId);
    

}
