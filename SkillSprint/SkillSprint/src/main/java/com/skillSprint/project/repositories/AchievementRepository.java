package com.skillSprint.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillSprint.project.models.Achievement;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    // You can add custom queries if needed in the future
}
