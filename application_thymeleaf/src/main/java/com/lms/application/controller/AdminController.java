package com.lms.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.application.entity.ApplicationUser;
import com.lms.application.entity.Course;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	@RequestMapping(method = RequestMethod.GET)
	public void register(RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("user", new ApplicationUser());
		model.addAttribute("course", new Course());
		//return "admin";
	}
	
	

}
