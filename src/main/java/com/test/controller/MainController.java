package com.test.controller;

import com.test.entity.Customer;
import com.test.service.CustomerService;
import com.test.service.LoginService;
import com.test.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	@RequestMapping({"", "/"})
	public ModelAndView index(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "warning", required = false) String warning,
							  @RequestParam(value = "success", required = false) String success) {
		// mayby try to push all messages in "msg" parameter

		Map<String, Object> model = new HashMap<>();
		model.put("customer", new Customer());
		return new ModelAndView("tl/main", model);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("registerForm") Customer registerForm, BindingResult bindingResult, Model model) {
		customerValidator.validate(registerForm, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", bindingResult.getAllErrors());
			model.addAttribute("customer", registerForm);
			return "tl/main";
		}
		customerService.encodePasswordsAndSave(registerForm);
		loginService.autologin(registerForm.getEmail(), registerForm.getPasswordConfirm());

		return "redirect:/cabinet";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
		binder.registerCustomEditor(Date.class, editor);
	}
}