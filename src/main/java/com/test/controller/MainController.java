package com.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/*")
	public ModelAndView p4042(Model model) {
		ModelAndView modelAndView = new ModelAndView("tl/404");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		System.err.println("p4042");
		return modelAndView;
	}

	@RequestMapping("/")
	public String index(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "warning", required = false) String warning,
						@RequestParam(value = "success", required = false) String success,
						Model model) {
		model.addAttribute("error", error);
		model.addAttribute("warning", warning);
		model.addAttribute("success", success);
		return "tl/main";
	}
}