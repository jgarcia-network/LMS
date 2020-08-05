package com.lms.application.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lms.application.entity.LearningPlan;
import com.lms.application.service.LearningPlanService;

@Controller
@RequestMapping("users/{id}/plan")
public class LearningPlanController {
		
		@Autowired
		private LearningPlanService service;
		
		@RequestMapping(method=RequestMethod.GET)
		public String index(Model model, @PathVariable Long id) {
			LearningPlan userPlan = service.getUserPlan(id);
			model.addAttribute("userPlan", userPlan);
			//model.addAttribute("plan", service.getPlan());
			return "learningplan";
		}
		
//		public String getCourses(Model model) {
//			model.addAttribute("plan", service.getPlan());
//			return "LearningPlanView";
//		}
		
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Object> createPlan(@RequestBody Set <Long> courseIds, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.submitNewLearningPlan(courseIds, id), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
			}
		}
}
