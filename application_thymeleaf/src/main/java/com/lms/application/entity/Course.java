package com.lms.application.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lms.application.util.CourseStatus;

@Entity
public class Course {
	
	private Long id;
	private String title;
	private String credits;
	private CourseStatus status;
	
	@JsonIgnore
	private Set<LearningPlan> plan;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCredits() {
		return credits;
	}
	
	public void setCredits(String credits) {
		this.credits = credits;
	}
	
	//@OneToMany(mappedBy = "user")
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "learning_plan_courses",
	joinColumns = @JoinColumn(name = "courseId", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "planId", referencedColumnName = "id"))
	public Set<LearningPlan> getPlan() {
		return plan;
	}

	public void setPlan(Set<LearningPlan> plan) {
		this.plan = plan;
	}

	public CourseStatus getStatus() {
		return status;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}	

}
