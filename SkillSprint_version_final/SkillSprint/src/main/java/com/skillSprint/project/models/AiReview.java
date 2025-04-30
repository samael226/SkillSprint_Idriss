package com.skillSprint.project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String originalCode;

    @Lob
    private String aiResponse;

    private String type; // "review" or "correction"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Who requested it

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = true)
    private Hackathon hackathon; // optional link if tied to a hackathon

    private java.util.Date createdAt = new java.util.Date();
}
