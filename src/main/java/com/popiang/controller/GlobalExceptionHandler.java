package com.popiang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${message.app.exception}")
	private String exceptionMessage;

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.getModel().put("message", exceptionMessage);
		modelAndView.getModel().put("url", req.getRequestURL());
		modelAndView.getModel().put("exception", e);
		modelAndView.setViewName("app.exception");
		
		return modelAndView;
		
	}
	
}
