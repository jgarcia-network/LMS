//package com.lms.application;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/css/**", "/js/**");
//    }
//	
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin().loginPage("/login").permitAll()
//			.defaultSuccessUrl("/courses")
//			.and()
//			.logout()
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//			.logoutSuccessUrl("/login").permitAll();
//	}
//
//}
