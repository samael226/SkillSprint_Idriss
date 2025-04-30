package com.skillSprint.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillSprint.project.models.AiInteraction;
import com.skillSprint.project.repositories.AiInteractionRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai/history")
@RequiredArgsConstructor
public class AiInteractionController {

    private final AiInteractionRepository aiInteractionRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<AiInteraction>> getUserAiInteractions(@PathVariable Long userId) {
        List<AiInteraction> interactions = aiInteractionRepository.findByUserId(userId);
        return ResponseEntity.ok(interactions);
    }
}
