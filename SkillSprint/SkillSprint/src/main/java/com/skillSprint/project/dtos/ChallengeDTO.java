package com.skillSprint.project.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class ChallengeDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private String fullDescription;
    private String tags;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date registrationStartDate;
    private Date registrationDeadline;
    private String objectives;
    private String requirementTitle;
    private String requirementDescription;
    private String criteriaTitle;
    private String criteriaDescription;
    private String submissionRequirements;
    private String starterCodeTitle;
    private String starterCodeDescription;
    private String starterCodeLink;
    private String challengeLanguage1;
    private String challengeLanguage2;
    private String challengeLanguage3;
    private String challengeLanguage4;
    private String learningResourcesTitle1;
    private String learningResourcesDescription1;
    private String learningResourcesLink1;
    private String learningResourcesTitle2;
    private String learningResourcesDescription2;
    private String learningResourcesLink2;
    private String learningResourcesTitle3;
    private String learningResourcesDescription3;
    private String learningResourcesLink3;
    private String learningResourcesTitle4;
    private String learningResourcesDescription4;
    private String learningResourcesLink4;
    private Long hackathonId;
}
