package com.lms.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.LearningPlan;
import com.lms.application.entity.User;

public interface LearningPlanRepository extends CrudRepository<LearningPlan, Long> {
	
	public LearningPlan findByUserId(Long userId);
}
