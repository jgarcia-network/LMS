package com.lms.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms.application.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
}
