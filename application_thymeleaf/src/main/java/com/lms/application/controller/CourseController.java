package com.lms.application.controller;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.application.entity.Course;
import com.lms.application.entity.LearningPlan;
import com.lms.application.entity.ApplicationUser;
import com.lms.application.service.CourseService;
import com.lms.application.service.LearningPlanService;
import com.lms.application.service.UserService;

@Controller
@RequestMapping("/courses")
public class CourseController {
	
		@Autowired
		private CourseService service;
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private LearningPlanService LPservice;
		
//		@RequestMapping(method=RequestMethod.GET)
//		public ResponseEntity<Object> getCourses(){
//			return new ResponseEntity<Object>(service.getCourses(), HttpStatus.OK);
//		}
		
		@RequestMapping(method=RequestMethod.GET)
		public String showCourses(Model model) {
			model.addAttribute("course", service.getCourses());
			UserDetails userdetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ApplicationUser user = userService.getByUserName(userdetails.getUsername());
			LearningPlan userPlan = LPservice.getUserPlan(user.getId());
			return "courses";
		}	
		
		@RequestMapping(value="/create", method=RequestMethod.GET)
		public String showForm(Model model) {
			model.addAttribute("course", new Course());
			//return "createcourse";
			return "redirect:/courses/createcourse";
		}
		
		@RequestMapping(value="/create", method=RequestMethod.POST)
		public String createCourse(RedirectAttributes redirectAttributes, @ModelAttribute("course") Course course){
		    service.createCourse(course);
		    redirectAttributes.addFlashAttribute("success", "Course: " + "\"" + course.getTitle() + "\"" + " created");
		    //return "redirect:/courses/createcourse";
		    return "redirect:/admin";
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
		
		@RequestMapping(value="/add/{id}", method=RequestMethod.POST)
		public String addCourseToPlan(@PathVariable Long id, @ModelAttribute("course") Course course, RedirectAttributes redirectAttributes, Model model) {
			UserDetails userdetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ApplicationUser user = userService.getByUserName(userdetails.getUsername());
			LearningPlan userPlan = LPservice.getUserPlan(user.getId());
			
			System.out.println("Course Title " + course.getTitle());
			System.out.println("Course ID " + course.getId());
			System.out.println("Course Credits " + course.getCredits());
			System.out.println("User Plan is " + userPlan);
			
			System.out.println("This is the userplan " + user.getPlan().getId());
			
			boolean flag = false;
			
			Set<Course> courses = userPlan.getCourses();
			for (Course check : courses) {
				Long checkId = check.getId();
				if (checkId == id) {
					flag = true;
				}
			}
			
			if (flag != true) {
				Set<Long> courseId = new HashSet<Long>();
				courseId.add(id);
				LPservice.setPlanCourses(courseId, userPlan);
				redirectAttributes.addFlashAttribute("error", "Course added to your plan");
				return "redirect:/courses";
			} else {
				redirectAttributes.addFlashAttribute("error", "Course exists in your plan");
				return "redirect:/courses";
			}

			//return "courses";
			//return "redirect:/courses";
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
