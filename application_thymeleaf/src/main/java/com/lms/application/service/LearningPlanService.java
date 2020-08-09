package com.lms.application.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.application.entity.Course;
import com.lms.application.entity.LearningPlan;
import com.lms.application.entity.ApplicationUser;
import com.lms.application.repository.CourseRepository;
import com.lms.application.repository.LearningPlanRepository;
import com.lms.application.repository.UserRepository;
import com.lms.application.util.CourseStatus;

@Service
public class LearningPlanService {

	private static final Logger logger = LogManager.getLogger(LearningPlanService.class);

	@Autowired
	private LearningPlanRepository repo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CourseRepository courseRepo;

	public Iterable<LearningPlan> getPlan() {
		return repo.findAll();
	}

	public LearningPlan getUserPlan(Long userId) {
		LearningPlan lp = repo.findByUserId(userId);
		return lp == null ? new LearningPlan() : lp;
	}

	//public LearningPlan submitNewLearningPlan(Set<Long> courseIds, Long userId) {
	public LearningPlan submitNewLearningPlan(Long userId) {
		try {
		ApplicationUser currentUser = userRepo.findById(userId).orElse(null);
		if (currentUser != null) {
			//LearningPlan currentPlan = createPlan(courseIds, currentUser);
			LearningPlan currentPlan = createPlan(currentUser);
			return currentPlan;
		}
		} catch (Exception e) {
			logger.error("Exception occurred while trying to create plan");
			throw e;
		}
		return null;
	}

//	private LearningPlan setInProgress(Set<Long> courseIds, ApplicationUser user) {
//		LearningPlan plan = new LearningPlan();
//		Course course = new Course();
//		plan.setCourses(convertToCourseSet(courseRepo.findAllById(courseIds)));
//		plan.setDateAdded(LocalDate.now());
//		plan.setUser(user);
//		course.setStatus(CourseStatus.IN_PROGRESS);
//		addCourseToPlan(plan);
//		return plan;
//	}
//
//	private LearningPlan setCompleted(Set<Long> courseIds, ApplicationUser user) {
//		LearningPlan plan = new LearningPlan();
//		Course course = new Course();
//		plan.setCourses(convertToCourseSet(courseRepo.findAllById(courseIds)));
//		plan.setDateAdded(LocalDate.now());
//		plan.setUser(user);
//		course.setStatus(CourseStatus.COMPLETED);
//		addCourseToPlan(plan);
//		return plan;
//	}
	
	public LearningPlan setPlanCourses(Set<Long> courseIds, LearningPlan plan) {
		System.out.println("User plan is " + plan);
		System.out.println("Found courses are " + courseRepo.findAllById(courseIds));
		plan.setCourses(convertToCourseSet(courseRepo.findAllById(courseIds)));
		plan.setStatus(CourseStatus.IN_PROGRESS);
		addCourseToPlan(plan);
		repo.save(plan);
		return plan;
	}

	public void addCourseToPlan(LearningPlan plan) {
		Set<Course> courses = plan.getCourses();
		for (Course course : courses) {
			course.getPlan().add(plan);
		}
	}

	//private LearningPlan createPlan(Set<Long> courseIds, ApplicationUser user) {
	private LearningPlan createPlan(ApplicationUser user) {
		LearningPlan plan = new LearningPlan();
		plan.setCourses(null);
		plan.setDateAdded(LocalDate.now());
		plan.setUser(user);
//		Set<Course> courses = plan.getCourses();
//		for (Course course : courses) {
//			course.getPlan().add(plan);
//		}
		plan = repo.save(plan);
		return plan;
	}

	private Set<Course> convertToCourseSet(Iterable<Course> iterable) {
		Set<Course> set = new HashSet<Course>();
		for (Course course : iterable) {
			set.add(course);
		}
		return set;
		
	}

}
