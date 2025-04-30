package com.skillSprint.project.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a simple confirmation email.
     *
     * @param to recipient email address
     * @param subject email subject line
     * @param text plain-text body
     */
    public void sendEmail(String to, String subject, String text) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            mailSender.send(msg);
        } catch (MessagingException e) {
            // log appropriately in real code
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Convenience for hackathon registration confirmation.
     */
    public void sendRegistrationConfirmation(String to, String hackathonTitle) {
        String subject = "✅ SkillSprint: Registered for " + hackathonTitle;
        String body = "Hello!\n\nYou have successfully registered for the hackathon:\n\n"
            + hackathonTitle + "\n\nGood luck!\n\n– SkillSprint Team";
        sendEmail(to, subject, body);
    }
}
