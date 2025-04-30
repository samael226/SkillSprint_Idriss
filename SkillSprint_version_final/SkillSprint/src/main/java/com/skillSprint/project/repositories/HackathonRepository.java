package com.skillSprint.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillSprint.project.models.Hackathon;
import com.skillSprint.project.models.User;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
	
	int countByWinner(User winner);

	List<Hackathon> findByParticipantsContaining(User user);

}
