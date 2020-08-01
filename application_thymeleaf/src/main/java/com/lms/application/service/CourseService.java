package com.lms.application.service;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.application.entity.Course;
import com.lms.application.repository.CourseRepository;

@Service
public class CourseService {

	private static final Logger logger = LogManager.getLogger(CourseService.class);
	
	@Autowired
	private CourseRepository repo;
	
	public Optional <Course> getCourses(Long id){
		return repo.findById(id);
	}
	
	public Iterable <Course> getCourses(){
		return repo.findAll();
	}
	
	public Course createCourse(Course course) {
		return repo.save(course);
	}
	
	public Course updateCourse(Course course) throws Exception{
		try {
			Course oldCourse = repo.findById(course.getId()).orElse(null);
			if (oldCourse != null) {
			oldCourse.setCredits(course.getCredits());
			oldCourse.setTitle(course.getTitle());
			return repo.save(oldCourse);
			}
			return oldCourse;
		} catch (Exception e) {
			logger.error("Exception occurred while trying to update course: " + course.getId(), e);
			throw new Exception("Unable to update course.");
		}
	}
	
	public void deleteCourse(Long id) throws Exception {
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to delete course: " + id, e);
			throw new Exception("Unable to delete course.");
		}
	}
	
}
