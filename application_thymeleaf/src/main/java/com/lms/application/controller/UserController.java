package com.lms.application.controller;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.application.entity.ApplicationUser;
import com.lms.application.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService service;
	
//	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
//	public ResponseEntity<Object> createCustomer(@RequestBody User user) {
//		return new ResponseEntity<Object>(service.createUser(user), HttpStatus.CREATED);
//	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("user", new ApplicationUser());
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	 //public ResponseEntity<Object> register(@RequestBody Credentials cred){
	//public ResponseEntity<Object> register(@ModelAttribute("cred") Credentials cred){
	public String register(RedirectAttributes redirectAttributes, @ModelAttribute("user") ApplicationUser user) {
		try {
		//return new ResponseEntity<Object>(service.register(cred), HttpStatus.CREATED);
			service.register(user);
			//return "Login";
			redirectAttributes.addFlashAttribute("success", "Account successfully created!");
			return "redirect:/users/login";
		} catch (AuthenticationException e) {
		//return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
			return "register";
		}
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login(Model model) {
//		model.addAttribute("cred", new Credentials());
//		return "Login";
//	}
//	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(RedirectAttributes redirectAttributes, @ModelAttribute("cred") Credentials cred) {
//		try {
//			service.login(cred);
//			redirectAttributes.addAttribute("userId", cred.getUserId());
//			return "redirect:/users/{userId}/plan";
//		} catch (AuthenticationException e) {
//			redirectAttributes.addFlashAttribute("error", "Incorrect username or password");
//			return "redirect:/users/login";
//		}
//	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
		try {
			service.deleteUser(id);
			return new ResponseEntity<Object>("Successfully deleted customer with id: " + id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
