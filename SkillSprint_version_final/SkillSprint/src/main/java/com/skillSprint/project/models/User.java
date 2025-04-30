package com.skillSprint.project.models;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;

    @Column(unique = true)
    private String email;
    
    
    private String userIMG;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    

    @ManyToMany(mappedBy = "members")
    private Set<Team> teams = new HashSet<>();

    // 🔥 Gamification Fields
    private int xp = 0;           // Experience Points
    private int level = 1;        // Level (you can calculate based on XP later)


    // ========== GAMIFICATION RELATIONSHIPS ==========

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBadge> badges = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<XpHistory> xpHistory = new HashSet<>();

    // ========== HACKATHON RELATIONSHIPS ==========

    @ManyToMany(mappedBy = "participants")
    @Builder.Default
	private Set<Hackathon> hackathonsParticipated = new HashSet<>(); // Participated Hackathons
    
    
    
    
    
 // Add this to your User model:
    @OneToMany(mappedBy = "winner")
    private Set<Hackathon> hackathonsWon = new HashSet<>();


    // ========== AI INTERACTIONS ==========

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AiInteraction> aiInteractions = new HashSet<>();
    
    
    
    
    private Date createdAt;
    private Date updatedAt;

    public enum Role {
        REGULAR, ADMIN, JUDGE ,USER
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

