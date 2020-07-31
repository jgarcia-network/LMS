package com.lms.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.LearningPlan;

public interface LearningPlanRepository extends CrudRepository<LearningPlan, Long> {

}
