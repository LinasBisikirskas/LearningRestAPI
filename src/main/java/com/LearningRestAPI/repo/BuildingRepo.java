package com.LearningRestAPI.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearningRestAPI.model.Building;

@Repository
public interface BuildingRepo extends JpaRepository<Building, Long> {
    
}
