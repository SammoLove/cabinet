package com.test.controller;

import com.test.entity.Picture;
import com.test.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	CustomerRepository customerRepository;

	/*@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getUsers() {
		List<Customer> result = new ArrayList<>();
		customerRepository.findAll().forEach(result::add);
		return result;
	}*/

	@RequestMapping(method = RequestMethod.GET)
	public String showCabinet(Model model) {
		model.addAttribute("now", LocalDateTime.now());
		return "tl/cabinet";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addPictures(@ModelAttribute("pictureList") List<Picture> pictures, Model model) {
		//checking errors
		model.addAttribute("info", "pictures uploaded");
		return "tl/cabinet";
	}
}