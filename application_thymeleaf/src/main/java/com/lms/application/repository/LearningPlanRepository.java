package com.lms.application.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.LearningPlan;
import com.lms.application.entity.ApplicationUser;
import com.lms.application.entity.Course;

public interface LearningPlanRepository extends CrudRepository<LearningPlan, Long> {
	
	public LearningPlan findByUserId(Long userId);
}
