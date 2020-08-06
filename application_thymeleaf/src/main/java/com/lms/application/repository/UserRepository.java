package com.lms.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.ApplicationUser;

public interface UserRepository extends CrudRepository<ApplicationUser, Long> {
	
	public ApplicationUser findByUsername(String username);
	
}