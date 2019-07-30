package com.popiang.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.popiang.service.UserService;


//
// this class is the configuration of the security of access
//
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		
		http
			.authorizeRequests()
				.antMatchers("/", 
							 "/about", 
							 "/register", 
							 "/confirmregister",
							 "/invaliduser",
							 "/expiredtoken",
							 "/verifyemail",
							 "/js/**", 
							 "/css/**", 
							 "/img/**")
				.permitAll()
				.antMatchers("/addstatus", 
							 "/viewstatus", 
							 "/deletestatus", 
							 "/editstatus")
				.hasRole("ADMIN")
				.antMatchers("/profile",
							 "/profile/*",
							 "/edit-profile-about",
							 "/upload-profile-photo",
							 "/profilephoto/*")
				.authenticated()
				.anyRequest()
				.denyAll()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
			.logout()
				.permitAll();

		// @formatter:on

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// managing user service for authentication			// encode the password
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
		
	}
	
}









