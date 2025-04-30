package com.skillSprint.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.skillSprint.project.events.GamificationEvent;
import com.skillSprint.project.models.Badge;
import com.skillSprint.project.models.User;
import com.skillSprint.project.models.UserBadge;
import com.skillSprint.project.models.UserSkill;
import com.skillSprint.project.models.XpHistory;
import com.skillSprint.project.repositories.BadgeRepository;
import com.skillSprint.project.repositories.UserBadgeRepository;
import com.skillSprint.project.repositories.UserRepository;
import com.skillSprint.project.repositories.UserSkillRepository;
import com.skillSprint.project.repositories.XpHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GamificationService {
    private final XpHistoryRepository xpRepo;
    private final UserSkillRepository skillRepo;
    private final BadgeRepository badgeRepo;
    private final UserBadgeRepository userBadgeRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final LevelingService levelingService;
    private final UserRepository userRepository;



    public void awardXp(Long userId, int amount, String source, String description, Long skillId) {
        // Save XP history
        xpRepo.save(XpHistory.builder()
                .userId(userId)
                .amount(amount)
                .source(source)
                .description(description)
                .build());

        // Update UserSkill (XP + Level)
        UserSkill skill = skillRepo.findByUserIdAndSkillId(userId, skillId)
                .orElse(UserSkill.builder()
                        .userId(userId)
                        .skillId(skillId)
                        .xp(0)
                        .level(1)
                        .build());
        skill.setXp(skill.getXp() + amount);
        skill.setLevel(calculateLevel(skill.getXp()));
        skillRepo.save(skill);
        
        
        User user = userRepository.findById(userId)
        	    .orElseThrow(() -> new RuntimeException("User not found"));

        	user.setXp(user.getXp() + amount);
        	levelingService.checkAndHandleLevelUp(user);

        	userRepository.save(user);


        // Unlock Badges
        checkAndUnlockBadges(userId);
    }

    private int calculateLevel(int totalXp) {
        return (totalXp / 100) + 1;
    }

    private void checkAndUnlockBadges(Long userId) {
        List<Badge> badges = new ArrayList<>(badgeRepo.findAll()); // Clone to avoid concurrent modification

        for (Badge badge : badges) {
            boolean alreadyEarned = userBadgeRepo.existsByUserIdAndBadgeId(userId, badge.getId());
            if (!alreadyEarned && meetsCriteria(userId, badge.getCriteriaCode())) {
                // Use safe collection logic here
                userBadgeRepo.save(UserBadge.builder()
                    .userId(userId)
                    .badgeId(badge.getId())
                    .build());
            }
        }
    }



    private boolean meetsCriteria(Long userId, String criteriaCode) {
        if (criteriaCode.equals("FIRST_SUB")) {
            return xpRepo.findByUserId(userId).stream()
                    .anyMatch(xp -> xp.getSource().equals("challenge_complete"));
        }
        if (criteriaCode.equals("TEN_CHALLENGES")) {
            return xpRepo.findByUserId(userId).stream()
                    .filter(xp -> xp.getSource().equals("challenge_complete"))
                    .count() >= 10;
        }
        return false;
    }
    
    public void awardXp(User user, int xpAwarded, String reason) {
    			// Update XP in User
    			user.setXp(user.getXp() + xpAwarded);
    	        levelingService.checkAndHandleLevelUp(user);

    	        				// Save XP history
    	        xpRepo.save(XpHistory.builder()
		                .userId(user.getUserId())
		                .amount(xpAwarded)
		                .source(reason)
		                .description(reason)
		                .build());
    	        
        GamificationEvent event = new GamificationEvent(this, user, xpAwarded, reason);
        eventPublisher.publishEvent(event);
    }
}
