package com.skillSprint.project.controllers;

import com.skillSprint.project.dtos.ChallengeDTO;
import com.skillSprint.project.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<List<ChallengeDTO>> getAll() {
        return ResponseEntity.ok(challengeService.getAllChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }

    @PostMapping
    public ResponseEntity<ChallengeDTO> create(@RequestBody ChallengeDTO dto) {
        return ResponseEntity.ok(challengeService.createChallenge(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDTO> update(@PathVariable Long id, @RequestBody ChallengeDTO dto) {
        return ResponseEntity.ok(challengeService.updateChallenge(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }
}
