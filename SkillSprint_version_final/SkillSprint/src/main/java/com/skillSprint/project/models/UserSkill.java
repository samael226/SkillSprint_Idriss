package com.skillSprint.project.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_skills")
@IdClass(UserSkillId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkill {
    @Id
    private Long userId;

    @Id
    private Long skillId; // 1 = Frontend, 2 = Backend, etc.

    private int xp;

    private int level;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
