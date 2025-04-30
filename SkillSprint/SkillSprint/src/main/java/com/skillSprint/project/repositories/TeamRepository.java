package com.skillSprint.project.repositories;

import com.skillSprint.project.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
