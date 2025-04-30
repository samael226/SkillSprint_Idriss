package com.skillSprint.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skillSprint.project.dtos.LeaderboardEntry;
import com.skillSprint.project.dtos.LeaderboardItem;
import com.skillSprint.project.models.XpHistory;

public interface XpHistoryRepository extends JpaRepository<XpHistory, Long> {
    List<XpHistory> findByUserId(Long userId);
    
    
    

}
