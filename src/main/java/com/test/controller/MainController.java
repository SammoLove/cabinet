package com.test.controller;

import com.test.entity.Customer;
import com.test.service.CustomerService;
import com.test.service.LoginService;
import com.test.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	private final CustomerValidator customerValidator;
	private final CustomerService customerService;
	private final LoginService loginService;

	@Autowired
	public MainController(CustomerValidator customerValidator, CustomerService customerService, LoginService loginService) {
		this.customerValidator = customerValidator;
		this.customerService = customerService;
		this.loginService = loginService;
	}

	@RequestMapping("/*")
	public ModelAndView p404(Model model, HttpServletRequest sr) {
		ModelAndView modelAndView = new ModelAndView("tl/404");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		System.err.println("p4042" + sr.getServerName() + " " + sr.getLocalAddr() + " " + sr.getRequestURI() + " " + sr.getQueryString());
		return modelAndView;
	}

	@RequestMapping("/")
	public String index(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "warning", required = false) String warning,
						@RequestParam(value = "success", required = false) String success,
						Model model) {
		if (error != null) {
			if (error.equals("PWRequired")) {
				//сделать обработку JS сразу на странице!
				model.addAttribute("error", "PWRequired");
			}
		}


		// mayby try to lay all messages in "msg" parameter

		model.addAttribute("error", error);
		model.addAttribute("warning", warning);
		model.addAttribute("success", success);

		model.addAttribute("userForm", new Customer());
		return "tl/main";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("registerForm") Customer registerForm, BindingResult bindingResult, Model model) {
		customerValidator.validate(registerForm, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Some register error");
			return "tl/main";
		}
		customerService.encodePasswordsAndSave(registerForm);
		loginService.autologin(registerForm.getEmail(), registerForm.getPasswordConfirm());

		return "redirect:/cabinet";
	}
}