package com.skillSprint.project.repositories;

import com.skillSprint.project.models.UserSkill;
import com.skillSprint.project.models.UserSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, UserSkillId> {
    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);
    List<UserSkill> findByUserId(Long userId);
}
