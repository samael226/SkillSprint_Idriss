package com.skillSprint.project.models;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "submissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the user who submitted
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Link to the challenge being submitted
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Lob
    private String code;

    private String language;

    private int score;

    private String status; // e.g., "PENDING", "PASSED", "FAILED"

    private String feedback; // optional, for judges/AI

    private LocalDateTime submittedAt;
    
    @Column(columnDefinition = "TEXT")
    private String judgeFeedback;

    private Date judgedAt;

}
