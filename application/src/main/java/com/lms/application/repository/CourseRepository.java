package com.lms.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long>{

}
