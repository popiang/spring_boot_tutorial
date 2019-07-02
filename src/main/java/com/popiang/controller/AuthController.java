package com.popiang.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.model.SiteUser;
import com.popiang.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		return "app.login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) {
		
		SiteUser user = new SiteUser();
		
		modelAndView.getModel().put("user", user);
		modelAndView.setViewName("app.register");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(ModelAndView modelAndView, @Valid SiteUser user, BindingResult result) {

		modelAndView.setViewName("app.register");
		
		if(!result.hasErrors()) {
			userService.register(user);
			modelAndView.setViewName("redirect:/");
		}
		
		return modelAndView;
	}
	
}
