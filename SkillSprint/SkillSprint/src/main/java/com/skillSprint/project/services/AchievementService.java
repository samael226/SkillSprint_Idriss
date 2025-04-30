package com.skillSprint.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skillSprint.project.models.Achievement;
import com.skillSprint.project.models.User;
import com.skillSprint.project.models.UserAchievement;
import com.skillSprint.project.repositories.AchievementRepository;
import com.skillSprint.project.repositories.UserAchievementRepository;
import com.skillSprint.project.repositories.HackathonRepository;
import com.skillSprint.project.repositories.XpHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepo;
    private final UserAchievementRepository userAchievementRepo;
    private final HackathonRepository hackathonRepo;
    private final XpHistoryRepository xpHistoryRepo;

    public void checkAchievements(User user) {
        List<Achievement> achievements = achievementRepo.findAll();

        for (Achievement achievement : achievements) {
            boolean alreadyEarned = userAchievementRepo.existsByUserIdAndAchievementId(user.getUserId(), achievement.getId());
            if (alreadyEarned) continue;

            switch (achievement.getCriteriaCode()) {
                case "FIRST_LEVEL_UP":
                    if (user.getLevel() >= 2) {
                        awardAchievement(user, achievement);
                    }
                    break;
                case "REACH_500_XP":
                    int totalXp = xpHistoryRepo.findByUserId(user.getUserId()).stream()
                            .mapToInt(xp -> xp.getAmount())
                            .sum();
                    if (totalXp >= 500) {
                        awardAchievement(user, achievement);
                    }
                    break;
                case "WIN_FIRST_HACKATHON":
                    if (getHackathonsWon(user) >= 1) {
                        awardAchievement(user, achievement);
                    }
                    break;
                default:
                    // Handle more achievement types here
                    break;
            }
        }
    }

    private void awardAchievement(User user, Achievement achievement) {
        userAchievementRepo.save(UserAchievement.builder()
                .userId(user.getUserId())
                .achievementId(achievement.getId())
                .build());

        // You could trigger a notification here or event
        System.out.println("🎉 User " + user.getEmail() + " earned achievement: " + achievement.getTitle());
    }

    private int getHackathonsWon(User user) {
        return hackathonRepo.countByWinner(user);
    }
}
