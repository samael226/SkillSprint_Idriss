package com.skillSprint.project.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AiRequestTrackerService {

    private final Map<Long, Map<LocalDate, Integer>> requestCounts = new HashMap<>();

    private static final int MAX_REQUESTS_PER_DAY = 5;

    public boolean canMakeRequest(Long userId) {
        LocalDate today = LocalDate.now();
        requestCounts.putIfAbsent(userId, new HashMap<>());

        Map<LocalDate, Integer> userRequests = requestCounts.get(userId);
        int count = userRequests.getOrDefault(today, 0);

        if (count >= MAX_REQUESTS_PER_DAY) {
            return false;
        }

        userRequests.put(today, count + 1);
        return true;
    }
}
