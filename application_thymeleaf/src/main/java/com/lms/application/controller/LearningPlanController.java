package com.lms.application.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lms.application.entity.ApplicationUser;
import com.lms.application.entity.Course;
import com.lms.application.entity.LearningPlan;
import com.lms.application.repository.CourseRepository;
import com.lms.application.repository.LearningPlanRepository;
import com.lms.application.service.CourseService;
import com.lms.application.service.LearningPlanService;
import com.lms.application.service.UserService;

@Controller
//@RequestMapping("users/{id}/plan")
@RequestMapping("users/plan")
public class LearningPlanController {
		
		@Autowired
		private LearningPlanService service;
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private CourseService courseService;
		
		@Autowired
		private CourseRepository courseRepo;
		
		@Autowired
		private LearningPlanRepository planRepo;
		
		
		@RequestMapping(method=RequestMethod.GET)
		public String index(@ModelAttribute("course") Course course, RedirectAttributes redirectAttributes, Model model) {
			model.addAttribute("course", new Course());
			UserDetails userdetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ApplicationUser user = userService.getByUserName(userdetails.getUsername());
			LearningPlan userPlan = service.getUserPlan(user.getId());
			redirectAttributes.addFlashAttribute("id", user.getId());
			model.addAttribute("userPlan", userPlan);	
			model.addAttribute("firstname", user.getFirstName());
			model.addAttribute("lastname", user.getLastName());
			return "learningplan";
			//return "redirect:/users/plan";
		}
		
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Object> createPlan(@PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(service.submitNewLearningPlan(id), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
			}
		}
		
		@RequestMapping(value="delete/{id}", method=RequestMethod.POST)
		public String deletePlanCourse(@PathVariable Long id, @ModelAttribute("course") Course course, RedirectAttributes redirectAttributes){
				try {
				System.out.println("Course is " + course);
				UserDetails userdetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				ApplicationUser user = userService.getByUserName(userdetails.getUsername());
				LearningPlan userPlan = service.getUserPlan(user.getId());
				//service.removeCourse(id, userPlan, course, user);
				//course.removeCourse(userPlan);
				Course item = courseService.getCourses(id);
				//userPlan.getCourses().remove(course);
				service.removeCourse(userPlan, item);
				//service.deleteCourse(userPlan.getId(), course.getId());
				
//				Set <Course> courses = userPlan.getCourses();
//				//System.out.println("Id is " + id);
//				//System.out.println("Plan ID is " + userPlan.getId());
//				for (Course item : courses) {
//					//System.out.println("Get id is " + item.getId());
//					if (course.getId() == item.getId()) {
//						System.out.println("Match");
//						System.out.println("userPlan course title is " + item.getTitle());
//					
//						planRepo.save(userPlan);
//					}
//				}
				
				
				
				redirectAttributes.addFlashAttribute("success", "Course deleted from your plan");
				} catch (Exception e) {
					System.out.println(e);
				redirectAttributes.addFlashAttribute("error", "Course could not be removed");
				//return "redirect:/learningplan";
				}
				return "redirect:/users/plan";
		}
		
//		@RequestMapping(value="/add/{id}", method=RequestMethod.POST)
//		public String addCourseToPlan(@ModelAttribute("course") Course course, @PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
//			UserDetails userdetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			ApplicationUser user = userService.getByUserName(userdetails.getUsername());
//			LearningPlan userPlan = service.getUserPlan(user.getId());
//			model.getAttribute("course", course);
//			service.addCourseToPlan(course, userPlan);
//			System.out.println(model);
//			return "courses";
//		}
}
