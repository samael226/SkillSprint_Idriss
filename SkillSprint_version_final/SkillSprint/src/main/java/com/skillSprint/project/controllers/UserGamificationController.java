package com.skillSprint.project.controllers;

import com.skillSprint.project.models.UserBadge;
import com.skillSprint.project.models.UserSkill;
import com.skillSprint.project.models.XpHistory;
import com.skillSprint.project.repositories.UserBadgeRepository;
import com.skillSprint.project.repositories.UserSkillRepository;
import com.skillSprint.project.repositories.XpHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/gamification")
@RequiredArgsConstructor
public class UserGamificationController {

    private final XpHistoryRepository xpRepo;
    private final UserBadgeRepository badgeRepo;
    private final UserSkillRepository skillRepo;

    @GetMapping("/xp-history")
    public List<XpHistory> getXpHistory(@PathVariable Long userId) {
        return xpRepo.findByUserId(userId);
    }

    @GetMapping("/badges")
    public List<UserBadge> getBadges(@PathVariable Long userId) {
        return badgeRepo.findByUserId(userId);
    }

    @GetMapping("/skills")
    public List<UserSkill> getSkills(@PathVariable Long userId) {
        return skillRepo.findByUserId(userId);
    }
}
