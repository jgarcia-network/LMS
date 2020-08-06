package com.lms.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ApplicationUser {
	
	private Long id;
	@Column(unique=true)
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String hash;
	private LearningPlan plan;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;	
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore
	public String getHash() {
		return hash;
	}
	
	@JsonProperty
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	@OneToOne(mappedBy = "user")
	public LearningPlan getPlan() {
		return plan;
	}

	public void setPlan(LearningPlan plan) {
		this.plan = plan;
	}

}
