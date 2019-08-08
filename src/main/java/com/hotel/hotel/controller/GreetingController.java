package com.hotel.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="Hotel", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("Hotel", name);
		return "greeting";
	}

	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
