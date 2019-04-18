package com.popiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

	@RequestMapping("/admin")
	public String auth() {
		return "app.admin";
	}
	
}
