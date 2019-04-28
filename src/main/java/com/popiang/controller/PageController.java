package com.popiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.model.StatusUpdate;
import com.popiang.service.StatusUpdateService;

@Controller
public class PageController {
	
	@Autowired
	private StatusUpdateService service;

	@RequestMapping("/")
	public String home() {
		return "app.homepage";
	}

	@RequestMapping("/about")
	public String about() {
		return "app.about";
	}
	
	@RequestMapping(value = "/addstatus", method = RequestMethod.GET)
	public ModelAndView addStatus(ModelAndView modelAndView) {
		
		modelAndView.setViewName("app.addstatus");
		
		StatusUpdate statusUpdate = new StatusUpdate();
		StatusUpdate latestStatusUpdate = service.getLatest();
		
		modelAndView.addObject("statusUpdate", statusUpdate);
		modelAndView.addObject("latestStatusUpdate", latestStatusUpdate);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	public ModelAndView addStatus(ModelAndView modelAndView, StatusUpdate statusUpdate) {

		service.save(statusUpdate);

		modelAndView.setViewName("app.addstatus");
		
		statusUpdate = new StatusUpdate();
		StatusUpdate latestStatusUpdate = service.getLatest();
		
		modelAndView.addObject("statusUpdate", statusUpdate);
		modelAndView.addObject("latestStatusUpdate", latestStatusUpdate);		
		
		return modelAndView;
	}	
	
}
