package com.skillSprint.project.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skillSprint.project.events.GamificationEvent;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GamificationListener {

    private final UserRepository userRepository;

    @EventListener
    @Transactional
    public void handleGamificationEvent(GamificationEvent event) {
        User user = event.getUser();
        int newXp = user.getXp() + event.getXpAwarded();
        user.setXp(newXp);
        userRepository.save(user);
        // Optionally you can log or send a notification
        System.out.println("Awarded " + event.getXpAwarded() + " XP to " + user.getEmail() + " for: " + event.getReason());
    }
}
