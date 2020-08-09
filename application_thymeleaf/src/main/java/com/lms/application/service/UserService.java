package com.lms.application.service;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.application.entity.Credentials;
import com.lms.application.entity.ApplicationUser;
import com.lms.application.repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	public ApplicationUser getByUserName(String username) {
		return repo.findByUsername(username);
	}
	
	public ApplicationUser register(ApplicationUser user) throws AuthenticationException {
		user.setId(user.getId());
		user.setEmail(user.getEmail());
		user.setFirstName(user.getFirstName());
		user.setLastName(user.getLastName());
		user.setUsername(user.getUsername());
		user.setHash(bCryptPasswordEncoder.encode(user.getHash()));
		//user.setHash(BCrypt.hashpw(user.getHash(), BCrypt.gensalt()));
		try {
			repo.save(user);
			return user;
		} catch (DataIntegrityViolationException e) {
			throw new AuthenticationException("Username taken.");
		}
	}

	public ApplicationUser login(Credentials cred) throws AuthenticationException {
		ApplicationUser foundUser = repo.findByUsername(cred.getUsername());
		if (foundUser != null && BCrypt.checkpw(cred.getPassword(), foundUser.getHash())) {
			cred.setUserId(foundUser.getId());
			return foundUser;
		} 
			throw new AuthenticationException("Invalid username or password.");
	}
	
	public void deleteUser(Long id) throws Exception {
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to delete customer: " + id, e);
			throw new Exception("Unable to delete customer.");
		}
	}
	
}
