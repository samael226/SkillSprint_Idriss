package com.skillSprint.project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillSprint.project.services.AiRequestTrackerService;
import com.skillSprint.project.services.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;
    private final AiRequestTrackerService trackerService;


    @PostMapping("/review")
    public ResponseEntity<String> reviewCode(
            @RequestParam String code,
            @RequestParam Long userId,
            @RequestParam(required = false) Long hackathonId) {
    	
    	if (!trackerService.canMakeRequest(userId)) {
            return ResponseEntity.status(429).body("Your AI request limit is reached for this hackathon . Try again in the next one.");
        }

        String aiFeedback = aiService.reviewCode(code, userId, hackathonId);
        return ResponseEntity.ok(aiFeedback);
    }

    @PostMapping("/correct")
    public ResponseEntity<String> correctCode(
            @RequestParam String code,
            @RequestParam Long userId,
            @RequestParam(required = false) Long hackathonId) {
    	
    	if (!trackerService.canMakeRequest(userId)) {
            return ResponseEntity.status(429).body("Your AI request limit is reached for this hackathon . Try again in the next one.");
        }

        String aiFeedback = aiService.correctCode(code, userId, hackathonId);
        return ResponseEntity.ok(aiFeedback);
    }
}
