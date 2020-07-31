package com.lms.application.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lms.application.service.LearningPlanService;

@RestController
@RequestMapping("users/{id}/plan")
public class LearningPlanController {
		
		@Autowired
		private LearningPlanService service;
		
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<Object> getPlan(){
			return new ResponseEntity<Object>(service.getPlan(), HttpStatus.OK);
		}
		
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Object> createPlan(@RequestBody Set <Long> courseIds, @PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.submitNewLearningPlan(courseIds, id), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
			}
		}		
}
