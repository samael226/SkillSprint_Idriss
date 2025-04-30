package com.skillSprint.project.services;

import com.skillSprint.project.dtos.ChallengeDTO;
import com.skillSprint.project.models.Challenge;
import com.skillSprint.project.models.Hackathon;
import com.skillSprint.project.repositories.ChallengeRepository;
import com.skillSprint.project.repositories.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepo;
    private final HackathonRepository hackathonRepo;

    public List<ChallengeDTO> getAllChallenges() {
        return challengeRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ChallengeDTO getChallengeById(Long id) {
        return challengeRepo.findById(id).map(this::toDTO).orElse(null);
    }

    public ChallengeDTO createChallenge(ChallengeDTO dto) {
        Challenge challenge = fromDTO(dto);
        if (dto.getHackathonId() != null) {
            Optional<Hackathon> hackathon = hackathonRepo.findById(dto.getHackathonId());
            hackathon.ifPresent(challenge::setHackathon);
        }
        return toDTO(challengeRepo.save(challenge));
    }

    public ChallengeDTO updateChallenge(Long id, ChallengeDTO dto) {
        return challengeRepo.findById(id).map(challenge -> {
            Challenge updated = fromDTO(dto);
            updated.setId(id);
            if (dto.getHackathonId() != null) {
                hackathonRepo.findById(dto.getHackathonId()).ifPresent(updated::setHackathon);
            }
            return toDTO(challengeRepo.save(updated));
        }).orElse(null);
    }

    public void deleteChallenge(Long id) {
        challengeRepo.deleteById(id);
    }

    private ChallengeDTO toDTO(Challenge c) {
        ChallengeDTO dto = new ChallengeDTO();
        // map fields here (copy paste from model)
        dto.setId(c.getId());
        dto.setTitle(c.getTitle());
        dto.setDescription(c.getDescription());
        dto.setCategory(c.getCategory());
        dto.setDifficulty(c.getDifficulty());
        dto.setFullDescription(c.getFullDescription());
        dto.setTags(c.getTags());
        dto.setStartDate(c.getStartDate());
        dto.setEndDate(c.getEndDate());
        dto.setStatus(c.getStatus());
        dto.setRegistrationStartDate(c.getRegistrationStartDate());
        dto.setRegistrationDeadline(c.getRegistrationDeadline());
        dto.setObjectives(c.getObjectives());
        dto.setRequirementTitle(c.getRequirement_title());
        dto.setRequirementDescription(c.getRequirement_description());
        dto.setCriteriaTitle(c.getCriteria_title());
        dto.setCriteriaDescription(c.getCriteria_description());
        dto.setSubmissionRequirements(c.getSubmission_requirements());
        dto.setStarterCodeTitle(c.getStarter_code_title());
        dto.setStarterCodeDescription(c.getStarter_code_description());
        dto.setStarterCodeLink(c.getStarter_code_link());
        dto.setChallengeLanguage1(c.getChallenge_language1());
        dto.setChallengeLanguage2(c.getChallenge_language2());
        dto.setChallengeLanguage3(c.getChallenge_language3());
        dto.setChallengeLanguage4(c.getChallenge_language4());
        dto.setLearningResourcesTitle1(c.getLearning_resources_title1());
        dto.setLearningResourcesDescription1(c.getLearning_resources_description1());
        dto.setLearningResourcesLink1(c.getLearning_resources_link1());
        dto.setLearningResourcesTitle2(c.getLearning_resources_title2());
        dto.setLearningResourcesDescription2(c.getLearning_resources_description2());
        dto.setLearningResourcesLink2(c.getLearning_resources_link2());
        dto.setLearningResourcesTitle3(c.getLearning_resources_title3());
        dto.setLearningResourcesDescription3(c.getLearning_resources_description3());
        dto.setLearningResourcesLink3(c.getLearning_resources_link3());
        dto.setLearningResourcesTitle4(c.getLearning_resources_title4());
        dto.setLearningResourcesDescription4(c.getLearning_resources_description4());
        dto.setLearningResourcesLink4(c.getLearning_resources_link4());
        if (c.getHackathon() != null) dto.setHackathonId(c.getHackathon().getId());
        return dto;
    }

    private Challenge fromDTO(ChallengeDTO dto) {
        return Challenge.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .difficulty(dto.getDifficulty())
                .fullDescription(dto.getFullDescription())
                .tags(dto.getTags())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .RegistrationStartDate(dto.getRegistrationStartDate())
                .RegistrationDeadline(dto.getRegistrationDeadline())
                .objectives(dto.getObjectives())
                .requirement_title(dto.getRequirementTitle())
                .requirement_description(dto.getRequirementDescription())
                .criteria_title(dto.getCriteriaTitle())
                .criteria_description(dto.getCriteriaDescription())
                .submission_requirements(dto.getSubmissionRequirements())
                .Starter_code_title(dto.getStarterCodeTitle())
                .Starter_code_description(dto.getStarterCodeDescription())
                .Starter_code_link(dto.getStarterCodeLink())
                .challenge_language1(dto.getChallengeLanguage1())
                .challenge_language2(dto.getChallengeLanguage2())
                .challenge_language3(dto.getChallengeLanguage3())
                .challenge_language4(dto.getChallengeLanguage4())
                .learning_resources_title1(dto.getLearningResourcesTitle1())
                .learning_resources_description1(dto.getLearningResourcesDescription1())
                .learning_resources_link1(dto.getLearningResourcesLink1())
                .learning_resources_title2(dto.getLearningResourcesTitle2())
                .learning_resources_description2(dto.getLearningResourcesDescription2())
                .learning_resources_link2(dto.getLearningResourcesLink2())
                .learning_resources_title3(dto.getLearningResourcesTitle3())
                .learning_resources_description3(dto.getLearningResourcesDescription3())
                .learning_resources_link3(dto.getLearningResourcesLink3())
                .learning_resources_title4(dto.getLearningResourcesTitle4())
                .learning_resources_description4(dto.getLearningResourcesDescription4())
                .learning_resources_link4(dto.getLearningResourcesLink4())
                .build();
    }
}
