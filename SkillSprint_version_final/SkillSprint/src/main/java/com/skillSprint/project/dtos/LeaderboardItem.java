package com.skillSprint.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a leaderboard entry for a challenge.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardItem {
    private Long teamId;
    private String teamName;
    private Integer score;
    private int totalXp;
    private int membersCount;
}
