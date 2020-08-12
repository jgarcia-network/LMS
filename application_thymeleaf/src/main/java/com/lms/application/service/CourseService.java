package com.lms.application.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.application.entity.Course;
import com.lms.application.entity.LearningPlan;
import com.lms.application.repository.CourseRepository;

@Service
public class CourseService {

	private static final Logger logger = LogManager.getLogger(CourseService.class);
	
	@Autowired
	private CourseRepository repo;
	
	public  Course getCourses(Long id){
		return repo.findCourseById(id);
	}
	
	public Iterable<Course> getCourses(){
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
	
	public void deleteCourse(Long courseId) throws Exception {
		try {
			repo.deleteById(courseId);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to delete product: " + courseId, e);
			throw new Exception("Unable to delete product.");
		}
	}
	
}
