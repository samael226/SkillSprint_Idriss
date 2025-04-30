package com.skillSprint.project.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "xp_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XpHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int amount; // + or - XP points

    private String source; // Example: "challenge_complete", "early_submission"

    private String description;

    private LocalDateTime earnedAt;

    @PrePersist
    protected void onCreate() {
        earnedAt = LocalDateTime.now();
    }
}
