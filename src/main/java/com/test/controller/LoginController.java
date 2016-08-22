package com.test.controller;

import com.test.entity.Customer;
import com.test.entity.CustomerSessionStorage;
import com.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
	private final LoginService loginService;
	private final CustomerSessionStorage customerSessionStorage;

	@Autowired
	public LoginController(LoginService loginService, CustomerSessionStorage customerSessionStorage) {
		this.loginService = loginService;
		this.customerSessionStorage = customerSessionStorage;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	String logout() {
		return "redirect:/?info=logout";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	ModelAndView login(@RequestParam("email") String email,
					   @RequestParam("password") String password,
					   @RequestParam(name = "shortSession", required = false) Boolean shortSession,
					   @RequestParam(name = "returnUrl", required = false) String returnUrl,
					   ServerProperties.Session session,
					   HttpServletResponse response,
					   Cookie cookie,
					   ServerProperties.Session.Cookie cookie2) {

		Customer customer = loginService.checkPassword(email, password);

//		session.getCookie();
//		session.isPersistent();
//		session.setTimeout(10_000);
//		response.addCookie(null);

		if (customer != null && customer.getId() > 0) { //if use was found

			if (shortSession == null) { //save cookies only if not checked short session
				cookie = new Cookie("userId", String.valueOf(customer.getId()));
				response.addCookie(cookie);
			}
			customerSessionStorage.setUserId(customer.getId());
			customerSessionStorage.setCustomerName(customer.getName());

			if (returnUrl.isEmpty() || returnUrl.equals("/logout")) {
				returnUrl = "/wall/id" + customer.getId();
			}
			return new ModelAndView("redirect:" + returnUrl, null);
		} else { // if user was not found - return to login page
			response.setCharacterEncoding("UTF-8");
			//req.setAttribute("wrong", Boolean.TRUE); // передать через errors?
			//model. add error
			Map<String, String> errors = new HashMap<>();
			errors.put("password", "incorrect email or password");
			errors.put("returnUrl", returnUrl);
			return new ModelAndView("/", errors);
		}
	}
}
