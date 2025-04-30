package com.skillSprint.project.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillSprint.project.models.Hackathon;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.UserRepository;
import com.skillSprint.project.services.HackathonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hackathons")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;
    private final UserRepository userRepo;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<Hackathon> createHackathon(@RequestBody Hackathon hackathon) {
        Hackathon created = hackathonService.createHackathon(hackathon);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Hackathon>> getAllHackathons() {
        List<Hackathon> hackathons = hackathonService.getAllHackathons();
        return ResponseEntity.ok(hackathons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hackathon> getHackathonById(@PathVariable Long id) {
        return hackathonService.getHackathonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<Hackathon> updateHackathon(@PathVariable Long id, @RequestBody Hackathon hackathon) {
        Hackathon updated = hackathonService.updateHackathon(id, hackathon);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHackathon(@PathVariable Long id) {
        hackathonService.deleteHackathon(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/start")
    public ResponseEntity<Hackathon> start(@PathVariable Long id) {
        return ResponseEntity.ok(hackathonService.startHackathon(id));
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<Hackathon> end(@PathVariable Long id) {
        return ResponseEntity.ok(hackathonService.endHackathon(id));
    }

    @PutMapping("/{id}/assign-challenges")
    public ResponseEntity<Hackathon> assignChallenges(
            @PathVariable Long id,
            @RequestBody List<Long> challengeIds) {
        return ResponseEntity.ok(hackathonService.assignChallenges(id, challengeIds));
    }
    
    
    @PutMapping("/{id}/assign-winner")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<Hackathon> assignWinner(
			@PathVariable Long id,
			@RequestBody Long winnerId) {
		return ResponseEntity.ok(hackathonService.assignWinner(id, winnerId));
	}
    
	@GetMapping("/count/{winnerId}")
	public ResponseEntity<Integer> countHackathonsWon(@PathVariable Long winnerId) {
		return ResponseEntity.ok(hackathonService.countHackathonsWon(winnerId));
	}
	
	/** User registers for hackathon */
    @PostMapping("/{id}/register")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Hackathon> register(
            @PathVariable Long id,
            Principal principal) {

        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Hackathon updated = hackathonService.registerParticipant(id, user.getUserId());
        return ResponseEntity.ok(updated);
    }

    /** User unregisters from hackathon */
    @PostMapping("/{id}/unregister")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Hackathon> unregister(
            @PathVariable Long id,
            Principal principal) {

        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Hackathon updated = hackathonService.unregisterParticipant(id, user.getUserId());
        return ResponseEntity.ok(updated);
    }

    /** View participants (Admin/Judge) */
    @GetMapping("/{id}/participants")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGE')")
    public ResponseEntity<Set<User>> participants(@PathVariable Long id) {
        return ResponseEntity.ok(hackathonService.listParticipants(id));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
