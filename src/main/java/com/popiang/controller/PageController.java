package com.popiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String home() {
		return "app.homepage";
	}

	@RequestMapping("/about")
	public String about() {
		return "app.about";
	}
	
}
