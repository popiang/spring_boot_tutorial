package com.popiang.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.model.SiteUser;
import com.popiang.service.EmailService;
import com.popiang.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${message.invalid.user}")
	private String invalidUserMessage;
	
	@Value("${message.expired.token}")
	private String expiredTokenMessage;
	
	@Value("${message.registration.confirmed}")
	private String registrationConfirmedMessage;	
	
	// display login page
	@RequestMapping("/login")
	public String login() {
		return "app.login";
	}
	
	// display verifyemail page after successful registration
	@RequestMapping("/verifyemail")
	public String verifyEmail() {
		return "app.verifyemail";
	}
	
	//
	// divert to message page and display invalid user message
	//
	@RequestMapping("/invaliduser")
	public ModelAndView invalidUser(ModelAndView modelAndView) {
		
		modelAndView.getModel().put("message", invalidUserMessage);
		modelAndView.setViewName("app.message");
		return modelAndView;
	}
	
	//
	// divert to message page and display expired token message
	//	
	@RequestMapping("/expiredtoken")
	public ModelAndView expiredToken(ModelAndView modelAndView) {
		
		modelAndView.getModel().put("message", expiredTokenMessage);
		modelAndView.setViewName("app.message");
		return modelAndView;
	}
	
	//
	// divert to message page and display registration confirmed message
	//	
	@RequestMapping("/registrationconfirmed")
	public ModelAndView registrationConfirmed(ModelAndView modelAndView) {
		
		modelAndView.getModel().put("message", registrationConfirmedMessage);
		modelAndView.setViewName("app.message");
		return modelAndView;
	}	
	
	//
	// divert control to registration page & sending along a siteuser model for the form
	//
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) {
		
		SiteUser siteUser = new SiteUser();
		
		modelAndView.getModel().put("siteUser", siteUser);
		modelAndView.setViewName("app.register");
		
		return modelAndView;
	}
	
	//
	// process the registration, validate the fields, register the user, 
	// send verification email, set enabled to true and divert control to verify email page
	//
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(ModelAndView modelAndView, @Valid SiteUser siteUser, BindingResult result) {

		modelAndView.setViewName("app.register");
		
		if(!result.hasErrors()) {
			userService.register(siteUser);
			emailService.sendVerificationEmail(siteUser.getEmail());
			modelAndView.setViewName("redirect:/verifyemail");
			
			siteUser.setEnabled(true);
			userService.save(siteUser);
		}
		
		return modelAndView;
	}
	
}
