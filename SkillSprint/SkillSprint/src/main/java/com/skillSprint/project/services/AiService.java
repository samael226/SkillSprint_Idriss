package com.skillSprint.project.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skillSprint.project.models.AiInteraction;
import com.skillSprint.project.models.AiReview;
import com.skillSprint.project.models.Hackathon;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.AiInteractionRepository;
import com.skillSprint.project.repositories.AiReviewRepository;
import com.skillSprint.project.repositories.HackathonRepository;
import com.skillSprint.project.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final AiReviewRepository aiReviewRepo;
    private final UserRepository userRepo;
    private final HackathonRepository hackathonRepo;
    private final AiInteractionRepository aiInteractionRepository;


    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // Helper method to call OpenAI
    private String callOpenAi(String code, String promptInstruction) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        // OpenAI expects "messages" array (role: user, content: "your message")
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new Object[] {
            Map.of("role", "user", "content", promptInstruction + "\n\n" + code)
        });

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(OPENAI_API_URL, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                var choices = (java.util.List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
        }
        return "Sorry, AI could not process your request.";
    }

    // Public method to review code
    public String reviewCode(String code, Long userId, Long hackathonId) {
        String aiResponse = callOpenAi(code, "Please review the following code and suggest improvements:");

        saveAiReview(code, aiResponse, "review", userId, hackathonId);

        return aiResponse;
    }

    // Public method to correct code
    public String correctCode(String code, Long userId, Long hackathonId) {
        String aiResponse = callOpenAi(code, "Please correct the following code and explain the corrections:");

        saveAiReview(code, aiResponse, "correction", userId, hackathonId);

        return aiResponse;
    }

    // Save AI Review/Correction into database
    private void saveAiReview(String code, String aiResponse, String type, Long userId, Long hackathonId) {
        User user = userRepo.findById(userId).orElse(null);
        Hackathon hackathon = (hackathonId != null) ? hackathonRepo.findById(hackathonId).orElse(null) : null;

        AiReview review = AiReview.builder()
                .originalCode(code)
                .aiResponse(aiResponse)
                .type(type)
                .user(user)
                .hackathon(hackathon)
                .build();

        aiReviewRepo.save(review);
    }
    
    
   
    public AiInteraction saveInteraction(Long userId, Long hackathonId, String aiResponse) {
        AiInteraction interaction = AiInteraction.builder()
                .userId(userId)
                .hackathonId(hackathonId)
                .aiResponse(aiResponse)
                .build();

        return aiInteractionRepository.save(interaction);
    }
}
