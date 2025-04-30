package com.skillSprint.project.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillId implements Serializable {
    private Long userId;
    private Long skillId;
}
