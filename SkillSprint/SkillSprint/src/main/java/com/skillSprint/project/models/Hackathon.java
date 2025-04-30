package com.skillSprint.project.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hackathons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private Date startDate;

    private Date endDate;
    
    private String status; // e.g., "UPCOMING", "ONGOING", "COMPLETED"
    
    private String hackathonIMG;


    @ManyToMany
    @JoinTable(
      name = "hackathon_team",
      joinColumns = @JoinColumn(name = "hackathon_id"),
      inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> participatingTeams;
    
    
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;
    
    
    
 // ←–– ADD PARTICIPANTS RELATIONSHIP
    @ManyToMany
    @Builder.Default

    @JoinTable(
        name = "hackathon_participants",
        joinColumns = @JoinColumn(name = "hackathon_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();
    
    
    
    
    @Column(nullable = false)
    private int capacity;

    public boolean isFull() {
        return participants != null && participants.size() >= capacity;
    }

    

}
