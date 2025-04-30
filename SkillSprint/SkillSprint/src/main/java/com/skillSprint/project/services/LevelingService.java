package com.skillSprint.project.services;

import org.springframework.stereotype.Service;

import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LevelingService {

    private final UserRepository userRepository;

    // XP needed to level up (basic example: 100 XP per level)
    private static final int XP_PER_LEVEL = 100;

    public void checkAndHandleLevelUp(User user) {
        int newLevel = (user.getXp() / XP_PER_LEVEL) + 1;

        if (newLevel > user.getLevel()) {
            user.setLevel(newLevel);
            userRepository.save(user);
            // Optional: send notification, achievement, etc
        }
    }
}
