package com.skillSprint.project.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_badges")
@IdClass(UserBadgeId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBadge {
    @Id
    private Long userId;

    @Id
    private Long badgeId;

    private LocalDateTime earnedAt;

    @PrePersist
    protected void onCreate() {
        earnedAt = LocalDateTime.now();
    }
}
