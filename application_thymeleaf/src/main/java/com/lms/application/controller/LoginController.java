package com.lms.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.application.entity.Credentials;
import com.lms.application.service.UserService;

@Controller
@RequestMapping(value ="/login")
public class LoginController {
	
	@Autowired
	UserService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("cred", new Credentials());
		return "/login";
	}
	
	@RequestMapping(value ="/failure", method = RequestMethod.GET)
	public String failure(RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("cred", new Credentials());
		redirectAttributes.addFlashAttribute("error", "Incorrect username or password");
		return "redirect:/login";
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	public String login(RedirectAttributes redirectAttributes, @ModelAttribute("cred") Credentials cred) {
//		try {
//			service.login(cred);
//			redirectAttributes.addAttribute("userId", cred.getUserId());
//			return "redirect:/users/{userId}/plan";
//		} catch (AuthenticationException e) {
//			redirectAttributes.addFlashAttribute("error", "Incorrect username or password");
//			return "redirect:/login";
//		}
//	}
	
}
