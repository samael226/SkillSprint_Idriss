package com.skillSprint.project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillSprint.project.dtos.EmailRequest;
import com.skillSprint.project.services.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * Generic endpoint to send any email. Useful for testing email configuration.
     */
    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getText());
        return ResponseEntity.ok().build();
    }

    /**
     * System-triggered endpoint to send hackathon registration confirmation.
     * Can be invoked to simulate registration emails.
     */
    @PostMapping("/send-registration")
    public ResponseEntity<Void> sendRegistrationConfirmation(
            @RequestParam String to,
            @RequestParam String hackathonTitle) {
        emailService.sendRegistrationConfirmation(to, hackathonTitle);
        return ResponseEntity.ok().build();
    }
}