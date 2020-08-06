package com.lms.application;

	
	import org.springframework.context.annotation.Configuration;
	import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
	import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

	@Configuration
	public class MvcConfig implements WebMvcConfigurer {

		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/").setViewName("learningplan");
			registry.addViewController("/learningplan").setViewName("learningplan");
			registry.addViewController("/login").setViewName("login");
		}

	}
