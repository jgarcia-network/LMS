//package com.lms.application.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.IdClass;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import com.lms.application.util.CourseStatus;
//
//@Entity
//public class LearningPlanCourses {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//	
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "courseId")
//	private Course course;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "planId")
//    private LearningPlan plan;
//
//    private CourseStatus status;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Course getCourse() {
//		return course;
//	}
//
//	public void setCourse(Course course) {
//		this.course = course;
//	}
//
//	public LearningPlan getPlan() {
//		return plan;
//	}
//
//	public void setPlan(LearningPlan plan) {
//		this.plan = plan;
//	}
//
//	public CourseStatus getStatus() {
//		return status;
//	}
//
//	public void setStatus(CourseStatus status) {
//		this.status = status;
//	}
//    
//    
//
//}
