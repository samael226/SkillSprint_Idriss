package com.skillSprint.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaderboardEntryDto {
    private String username;
    private int totalXp;
    private int level;
}
