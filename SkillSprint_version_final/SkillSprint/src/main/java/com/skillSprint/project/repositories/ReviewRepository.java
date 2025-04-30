// src/main/java/com/skillSprint/project/repositories/ReviewRepository.java
package com.skillSprint.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillSprint.project.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.judge.id = :judgeId AND r.score IS NULL")
    List<Review> findPendingByJudge(@Param("judgeId") Long judgeId);

    @Query("SELECT r FROM Review r WHERE r.judge.id = :judgeId")
    List<Review> findByJudge(@Param("judgeId") Long judgeId);
}
