package com.test.controller;

import com.test.entity.Customer;
import com.test.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cabinet*")
@PreAuthorize("hasRole('CUSTOMER')")
public class CabinetController {
	@Autowired
	CustomerRepository customerRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getUsers() {
		List<Customer> result = new ArrayList<>();
		customerRepository.findAll().forEach(result::add);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Customer addUser(String username, String password, String password_confirm) {
		//no empty fields allowed
		if (username.isEmpty() || password.isEmpty() || password_confirm.isEmpty())
			return null;
		//passwords should match
		if (!password.equals(password_confirm))
			return null;

		return customerRepository.save(new Customer(null, null, null, 0, null, null, null, null));
		//return customerRepository.create(new Customer(name, surname, birth, email, password, salt, data));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView getUserForm() {
		return new ModelAndView("add");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		customerRepository.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Customer getUser(@PathVariable("id") long id) {
		return customerRepository.findOne(id);
	}
}