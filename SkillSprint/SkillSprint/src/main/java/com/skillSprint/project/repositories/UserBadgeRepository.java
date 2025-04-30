package com.skillSprint.project.repositories;

import com.skillSprint.project.models.UserBadge;
import com.skillSprint.project.models.UserBadgeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, UserBadgeId> {
    boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
    List<UserBadge> findByUserId(Long userId);
}
