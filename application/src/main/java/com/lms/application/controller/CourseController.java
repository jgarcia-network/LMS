package com.lms.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lms.application.entity.Course;
import com.lms.application.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

		@Autowired
		private CourseService service;
		
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<Object> getCourses(){
			return new ResponseEntity<Object>(service.getCourses(), HttpStatus.OK);
		}
		
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Object> createCourses(@RequestBody Course course){
			return new ResponseEntity<Object>(service.createCourse(course), HttpStatus.CREATED);
		}
		
		@RequestMapping(value="/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Object> updateCourse(@PathVariable Long id, @RequestBody Course course){
			try {
				return new ResponseEntity<Object>(service.updateCourse(course), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Object>("Unable to update course.", HttpStatus.BAD_REQUEST);
			}		
		}
		
		@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Object> deleteCourse(@PathVariable Long id){
			try {
				service.deleteCourse(id);
				return new ResponseEntity<Object>("Successfully deleted course with id: " + id, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Object>("Unable to update course.", HttpStatus.BAD_REQUEST);
			}		
		}
		
//		@RequestMapping(value="/{id}", method=RequestMethod.PUT)
//		public ResponseEntity<Object> updatePlan(@PathVariable Long id, @RequestBody Course course, @PathVariable User user){
//			try {
//				if (course.getStatus().equals(CourseStatus.IN_PROGRESS)) {
//					return new ResponseEntity<Object>(service.updateCourse(course, user), HttpStatus.OK);
//				} else if (course.getStatus().equals(CourseStatus.COMPLETED)) {
//					return new ResponseEntity<Object>(service.setCompleted(id), HttpStatus.OK);
//				}
//				return new ResponseEntity<Object>("Invalid update request.", HttpStatus.BAD_REQUEST);
//			} catch (Exception e) {
//				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
//			}
//		}

}
