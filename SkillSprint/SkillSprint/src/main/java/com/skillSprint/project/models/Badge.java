package com.skillSprint.project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String iconUrl; // (Optional) URL to badge image

    private String criteriaCode; // Like "FIRST_SUB", "TEN_CHALLENGES"
}
