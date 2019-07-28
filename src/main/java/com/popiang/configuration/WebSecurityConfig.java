package com.popiang.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.popiang.service.UserService;

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
				.antMatchers("/addstatus", "/viewstatus", "/deletestatus", "/editstatus")
				.hasRole("ADMIN")
				.antMatchers("/profile", "/edit-profile-about")
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

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		
//		// @formatter:off
//		
//		auth
//			.inMemoryAuthentication()
//			.withUser("shahril")
//			.password(encoder.encode("hello"))
//			.roles("USER");
//		
//		// @formatter:on
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// managing user service for authentication			// encode the password
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
		
	}
	
}









