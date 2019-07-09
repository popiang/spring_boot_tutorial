package com.popiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.model.StatusUpdate;
import com.popiang.service.StatusUpdateService;

@Controller
public class PageController {

	@Autowired
	private StatusUpdateService service;
	
	//
	// display landing page after registration, display latest update
	//
	@RequestMapping("/")
	public ModelAndView home(ModelAndView modelAndView) {
		
		StatusUpdate statusUpdate = service.getLatest();
		modelAndView.getModel().put("statusUpdate", statusUpdate);
		modelAndView.setViewName("app.homepage");

		return modelAndView;
	}

	//
	// display about page
	//
	@RequestMapping("/about")
	public String about() {
		return "app.about";
	}

}
