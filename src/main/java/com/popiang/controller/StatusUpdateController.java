package com.popiang.controller;

import java.util.Date;

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
public class StatusUpdateController {

	@Autowired
	private StatusUpdateService service;
	
	@RequestMapping(value = "/addstatus", method = RequestMethod.GET)
	public ModelAndView addStatus(ModelAndView modelAndView, @ModelAttribute("statusUpdate") StatusUpdate statusUpdate) {
		
		modelAndView.setViewName("app.addstatus");
		
		StatusUpdate latestStatusUpdate = service.getLatest();
		
		modelAndView.addObject("latestStatusUpdate", latestStatusUpdate);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	public ModelAndView addStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {
		
		modelAndView.setViewName("app.addstatus");
	
		if(!result.hasErrors()) {
			service.save(statusUpdate);
			statusUpdate = new StatusUpdate();
			modelAndView.addObject("statusUpdate", statusUpdate);
			modelAndView.setViewName("redirect:/viewstatus");
		}
		
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
	
	@RequestMapping(value = "/deletestatus", method = RequestMethod.GET)
	public ModelAndView deleteStatus(ModelAndView modelAndView, @RequestParam(name = "id") Long id) {		
		
		service.delete(id);
		
		modelAndView.setViewName("redirect:/viewstatus");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/editstatus", method = RequestMethod.GET)
	public ModelAndView editStatus(ModelAndView modelAndView, @RequestParam(name = "id") Long id) {
		
		StatusUpdate statusUpdate = service.get(id);
		
		modelAndView.getModel().put("statusUpdate", statusUpdate);
		
		modelAndView.setViewName("app.editstatus");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/editstatus", method = RequestMethod.POST)
	public ModelAndView editStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {
		
		modelAndView.setViewName("app.editstatus");
		
		if(!result.hasErrors()) {
			statusUpdate.setAdded(new Date());
			service.save(statusUpdate);
			modelAndView.setViewName("redirect:/viewstatus");
		}
		
		return modelAndView;
	}
	
}
