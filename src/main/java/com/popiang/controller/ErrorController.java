package com.popiang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	// setting the error message
	@Value("${message.access.denied}")
	private String accessDeniedMessage;

	//
	// handling access denied error
	//
	@RequestMapping("/403")
	public ModelAndView accessDenied(ModelAndView modelAndView) {
		
		modelAndView.getModel().put("message", accessDeniedMessage);
		modelAndView.setViewName("app.message");
		
		return modelAndView;
	}
	
}
