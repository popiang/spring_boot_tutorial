package com.popiang.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView addStatus(ModelAndView modelAndView, @ModelAttribute("statusUpdate") StatusUpdate statusUpdate) {
		
		modelAndView.setViewName("app.addstatus");
		
		StatusUpdate latestStatusUpdate = service.getLatest();
		
		modelAndView.addObject("latestStatusUpdate", latestStatusUpdate);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	public ModelAndView addStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {

		if(!result.hasErrors()) {
			service.save(statusUpdate);
			statusUpdate = new StatusUpdate();
			modelAndView.addObject("statusUpdate", statusUpdate);
		}
		
		modelAndView.setViewName("app.addstatus");
		StatusUpdate latestStatusUpdate = service.getLatest();
		modelAndView.addObject("latestStatusUpdate", latestStatusUpdate);		
		
		return modelAndView;
	}	
	
	@RequestMapping(value = "/viewstatus", method = RequestMethod.GET)
	public ModelAndView viewStatus(ModelAndView modelAndView, @RequestParam(name = "p", defaultValue = "1") int pageNumber) {
		
		Page<StatusUpdate> page = service.getPage(pageNumber);
		
		modelAndView.getModel().put("page", page);
		
		modelAndView.setViewName("app.viewstatus");
		
		return modelAndView;
	}
	
}
