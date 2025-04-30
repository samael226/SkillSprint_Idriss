package com.skillSprint.project.models;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "challenges")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotBlank
    private String category;
    
    @NotBlank
    private String difficulty;
    
    @NotBlank
    private String fullDescription;
    
    @NotBlank
    private String tags;
    
    private Date startDate;

    private Date endDate;
    
    private String status; // e.g., "UPCOMING", "ONGOING", "COMPLETED"
    
    private Date RegistrationStartDate;

    
    private Date RegistrationDeadline;
    
    private String objectives;
    
    private String requirement_title;
    
    private String requirement_description;
    
    private String criteria_title;
    
    private String criteria_description;
    
    private String submission_requirements; // e.g., "Github", "deployement_url", "project_link", "video_link"
    
    private String Starter_code_title; 
    
    private String Starter_code_description;
    
    private String Starter_code_link;
    
    private String challenge_language1;
    
    private String challenge_language2;
    
    private String challenge_language3;
    
    private String challenge_language4;
    
    
    private String learning_resources_title1;
    
    private String learning_resources_description1;
    
    private String learning_resources_link1;
    
    private String learning_resources_title2;
    
    private String learning_resources_description2;
    
    private String learning_resources_link2;
    
    private String learning_resources_title3;
    
    private String learning_resources_description3;
    
    private String learning_resources_link3;
    
    private String learning_resources_title4;
    
    private String learning_resources_description4;
    
    private String learning_resources_link4;
    
    

    
    

    

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

	public int getXpReward() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
