package com.skillSprint.project.dtos;

import com.skillSprint.project.models.User;

public interface LeaderboardEntry {
    User getUser();
    Long getXpTotal();
}
