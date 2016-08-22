package com.test.controller;

import com.test.entity.Picture;
import com.test.service.LoginService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cabinet*")
@PreAuthorize("hasRole('CUSTOMER')")
public class CabinetController {
	private final LoginService loginService;

	public CabinetController(LoginService loginService) {
		this.loginService = loginService;
	}

	/*@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getUsers() {
		List<Customer> result = new ArrayList<>();
		customerRepository.findAll().forEach(result::add);
		return result;
	}*/

	@RequestMapping(method = RequestMethod.GET)
	@Secured("hasRole('')")
	public String showCabinet(Model model) {
		model.addAttribute("now", LocalDateTime.now()); //todo delete this line
		if (loginService.ensureCustomer()) {
			return "tl/cabinet";
		} else {
			return "redirect:tl/main?error=NotCustomer";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addPictures(@ModelAttribute("pictureList") List<Picture> pictures, Model model) {
		//todo maybe need in checking errors here
		model.addAttribute("info", "pictures uploaded");
		return "tl/cabinet";
	}
}