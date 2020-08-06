package com.lms.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.LearningPlan;
import com.lms.application.entity.ApplicationUser;

public interface LearningPlanRepository extends CrudRepository<LearningPlan, Long> {
	
	public LearningPlan findByUserId(Long userId);
}
