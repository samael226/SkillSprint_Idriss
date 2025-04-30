package com.skillSprint.project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String criteriaCode; // Ex: "FIRST_WIN", "500_XP", "FIRST_LEVEL_UP"

    private String iconUrl; // optional: achievement icon
}
