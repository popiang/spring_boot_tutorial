package com.popiang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

//
// handling global exceptions
//
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Value("${message.app.exception}")
	private String exceptionMessage;
	
	@Value("${message.duplicate.user.exception}")
	private String duplicateUserMessage;
	
	@ExceptionHandler(value = MultipartException.class)
	@ResponseBody
	public String uploadPhotoHandler(Exception e) {
		
		e.printStackTrace();
		
		return "Error uploading the file!";
	}

	//
	// handling default exceptions
	//
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {

		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.getModel().put("message", exceptionMessage);
		modelAndView.getModel().put("url", req.getRequestURL());
		modelAndView.getModel().put("exception", e);
		modelAndView.setViewName("app.exception");
		
		return modelAndView;
		
	}
	
	//
	// handling duplicate user error during registration
	//
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ModelAndView duplicateUserErrorHandler(HttpServletRequest req, Exception e) {

		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.getModel().put("message", duplicateUserMessage);
		modelAndView.getModel().put("url", req.getRequestURL());
		modelAndView.getModel().put("exception", e);
		modelAndView.setViewName("app.exception");
		
		return modelAndView;
		
	}
	
}
