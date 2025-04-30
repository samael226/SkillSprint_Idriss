package com.skillSprint.project.services;

import com.skillSprint.project.dtos.TeamDTO;
import com.skillSprint.project.models.Team;
import com.skillSprint.project.models.User;
import com.skillSprint.project.repositories.TeamRepository;
import com.skillSprint.project.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TeamDTO createTeam(TeamDTO dto) {
        Team team = new Team();
        team.setName(dto.getName());
        Set<User> members = dto.getMemberIds().stream()
            .map(id -> userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id)))
            .collect(Collectors.toSet());
        team.setMembers(members);
        Team saved = teamRepository.save(team);
        return mapToDto(saved);
    }

    @PreAuthorize("hasAnyRole('ADMIN','REGULAR','JUDGE')")
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','REGULAR','JUDGE')")
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Team not found: " + id));
        return mapToDto(team);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TeamDTO updateTeam(Long id, TeamDTO dto) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Team not found: " + id));
        team.setName(dto.getName());
        Set<User> members = dto.getMemberIds().stream()
            .map(uid -> userRepository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + uid)))
            .collect(Collectors.toSet());
        team.setMembers(members);
        Team updated = teamRepository.save(team);
        return mapToDto(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    private TeamDTO mapToDto(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setMemberIds(
            team.getMembers().stream()
                .map(User::getUserId)
                .collect(Collectors.toSet())
        );
        return dto;
    }
}
