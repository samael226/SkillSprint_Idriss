package com.skillSprint.project.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.skillSprint.project.models.Challenge;
import com.skillSprint.project.models.Hackathon;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.ChallengeRepository;
import com.skillSprint.project.repositories.HackathonRepository;
import com.skillSprint.project.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final ChallengeRepository challengeRepo;
    private final UserRepository userRepo;
    private final EmailService emailService;



    public Hackathon createHackathon(Hackathon hackathon) {
        hackathon.setStatus("UPCOMING");

        return hackathonRepository.save(hackathon);
    }

    public List<Hackathon> getAllHackathons() {
        return hackathonRepository.findAll();
    }

    public Optional<Hackathon> getHackathonById(Long id) {
        return hackathonRepository.findById(id);
    }

    public Hackathon updateHackathon(Long id, Hackathon updatedHackathon) {
        Hackathon hackathon = hackathonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found"));

        hackathon.setTitle(updatedHackathon.getTitle());
        hackathon.setStartDate(updatedHackathon.getStartDate());
        hackathon.setEndDate(updatedHackathon.getEndDate());
        hackathon.setDescription(updatedHackathon.getDescription());
        // add any other fields you want to update

        return hackathonRepository.save(hackathon);
    }

    public void deleteHackathon(Long id) {
        hackathonRepository.deleteById(id);
    }
    
    public Hackathon startHackathon(Long id) {
        Hackathon hackathon = hackathonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));
        hackathon.setStatus("ONGOING");
        return hackathonRepository.save(hackathon);
    }

    public Hackathon endHackathon(Long id) {
        Hackathon hackathon = hackathonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));
        hackathon.setStatus("COMPLETED");
        return hackathonRepository.save(hackathon);
    }

    public Hackathon assignChallenges(Long hackathonId, List<Long> challengeIds) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
            .orElseThrow(() -> new RuntimeException("Hackathon not found"));
        List<Challenge> challenges = challengeRepo.findAllById(challengeIds);
        challenges.forEach(c -> c.setHackathon(hackathon));
        challengeRepo.saveAll(challenges);
        return hackathon;
    }
    

    /** Unregister a user from a hackathon */
    public Hackathon unregisterParticipant(Long hackathonId, Long userId) {
        Hackathon hack = hackathonRepository.findById(hackathonId)
            .orElseThrow(() -> new RuntimeException("Hackathon not found"));
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        hack.getParticipants().remove(user);
        return hackathonRepository.save(hack);
    }

    /** List participants of a hackathon */
    public Set<User> listParticipants(Long hackathonId) {
        Hackathon hack = hackathonRepository.findById(hackathonId)
            .orElseThrow(() -> new RuntimeException("Hackathon not found"));
        return hack.getParticipants();
    }

    //** assign the winner of the hackathon*/
    /** assign the winner of the hackathon */
    public Hackathon assignWinner(Long hackathonId, Long winnerId) {
		Hackathon hackathon = hackathonRepository.findById(hackathonId)
			.orElseThrow(() -> new RuntimeException("Hackathon not found"));
		User winner = userRepo.findById(winnerId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		hackathon.setWinner(winner);
		return hackathonRepository.save(hackathon);
	}

	/** Count the number of hackathons won by a user */
	public int countHackathonsWon(Long winnerId) {
		User winner = userRepo.findById(winnerId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return hackathonRepository.countByWinner(winner);
	}
	/** List all hackathons a user has participated in */
	public List<Hackathon> listHackathonsByParticipant(Long userId) {
		User user = userRepo.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return hackathonRepository.findByParticipantsContaining(user);
	}
	
	public Hackathon registerParticipant(Long hackathonId, Long userId) {
        Hackathon hack = hackathonRepository.findById(hackathonId)
            .orElseThrow(() -> new RuntimeException("Hackathon not found"));
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (hack.isFull()) {
            throw new RuntimeException("Hackathon is full");
        }

        hack.getParticipants().add(user);
        Hackathon saved = hackathonRepository.save(hack);

        // SYSTEM-TRIGGERED EMAIL
        emailService.sendRegistrationConfirmation(user.getEmail(), hack.getTitle());

        return saved;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
