package com.lms.application.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private LearningPlanService LPService;

	@RequestMapping(method = RequestMethod.GET)
	public String showCourses(Model model) {
		model.addAttribute("course", service.getCourses());
		UserDetails userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApplicationUser user = userService.getByUserName(userdetails.getUsername());
		LearningPlan userPlan = LPService.getUserPlan(user.getId());
		return "courses";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showForm(Model model) {
		model.addAttribute("course", new Course());
		return "redirect:/courses/createcourse";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createCourse(RedirectAttributes redirectAttributes, @ModelAttribute("course") Course course) {
		service.createCourse(course);
		redirectAttributes.addFlashAttribute("success", "Course: " + '"' + course.getTitle() + '"' + " created");
		return "redirect:/admin";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCourse(@PathVariable Long id, @RequestBody Course course) {
		try {
			return new ResponseEntity<Object>(service.updateCourse(course), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Unable to update course.", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
	public String addCourseToPlan(@PathVariable Long id, @ModelAttribute("course") Course course,
			RedirectAttributes redirectAttributes, Model model) {
		UserDetails userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApplicationUser user = userService.getByUserName(userdetails.getUsername());
		LearningPlan userPlan = LPService.getUserPlan(user.getId());
		Course item = service.getCourses(id);
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
			LPService.setPlanCourses(courseId, userPlan);
			redirectAttributes.addFlashAttribute("success", "Course: " + '"' + item.getTitle() +'"' + " added to your plan");
			return "redirect:/courses";
		} else {
			redirectAttributes.addFlashAttribute("error", "Course: " + '"' + item.getTitle() + '"' + " already exists in your plan");
			return "redirect:/courses";
		}
	}

}
