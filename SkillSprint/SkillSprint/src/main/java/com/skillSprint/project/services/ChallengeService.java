package com.skillSprint.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillSprint.project.models.Challenge;
import com.skillSprint.project.repositories.ChallengeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public Challenge createChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> getChallengeById(Long id) {
        return challengeRepository.findById(id);
    }

    public List<Challenge> getChallengesByHackathon(Long hackathonId) {
        return challengeRepository.findByHackathonId(hackathonId);
    }

    public Challenge updateChallenge(Long id, Challenge updatedChallenge) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

        challenge.setTitle(updatedChallenge.getTitle());
        challenge.setDescription(updatedChallenge.getDescription());
        challenge.setHackathon(updatedChallenge.getHackathon());

        return challengeRepository.save(challenge);
    }

    public void deleteChallenge(Long id) {
        challengeRepository.deleteById(id);
    }
}
