package com.popiang.controller;

import java.io.FileNotFoundException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.model.SiteUser;
import com.popiang.model.VerificationToken;
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
	
	// display verify email page after successful registration
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
	@RequestMapping("/confirmregister")
	public ModelAndView registrationConfirmed(ModelAndView modelAndView, @RequestParam("t") String stringToken) {
		
		VerificationToken token = userService.getVerificationToken(stringToken);
		
		if(token == null) {
			modelAndView.setViewName("redirect:/invaliduser");
			return modelAndView;
		}
		
		Date expiryDate = token.getExpiry();
		
		if(expiryDate.before(new Date())) {
			modelAndView.setViewName("redirect:/expiredtoken");
			userService.deleteToken(token);
			return modelAndView;
		}
		
		SiteUser siteUser = token.getUser();
		
		if(siteUser == null) {
			modelAndView.setViewName("redirect:/invaliduser");
			userService.deleteToken(token);
			return modelAndView;
		}
		
		userService.deleteToken(token);
		siteUser.setEnabled(true);
		userService.save(siteUser);
		
		modelAndView.getModel().put("message", registrationConfirmedMessage);
		modelAndView.setViewName("app.message");
		
		return modelAndView;
	}	
	
	//
	// divert control to registration page & sending along a siteuser model for the form
	//
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) throws FileNotFoundException {
		
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
			
			String token = userService.createEmailVerificationToken(siteUser);
			
			emailService.sendVerificationEmail(siteUser.getEmail(), token);
			modelAndView.setViewName("redirect:/verifyemail");
			
		}
		
		return modelAndView;
	}
	
}
