package com.skillSprint.project.repositories;

import com.skillSprint.project.models.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
	
}
