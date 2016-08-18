package com.test.controller;

import com.test.entity.Customer;
import com.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	private final LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping(value = "/logout111", method = RequestMethod.GET)
	ModelAndView logout(ModelAndView modelAndView) {
		// Spring Sec
		modelAndView.setViewName("tl/main");
		return modelAndView;
	}

	@RequestMapping(value = "/login111", method = RequestMethod.POST)
	ModelAndView login(@RequestParam("email") String email,
					   @RequestParam("password") String password,
					   @RequestParam(name = "shortSession", required = false) Boolean shortSession,
					   @RequestParam(name = "returnUrl", required = false) String returnUrl,
					   ModelAndView modelAndView) {

		Customer customer = loginService.checkPassword(email, password);


		/*if (customer != null && customer.getId() > 0) { //if use was found
			//Началист spring сессии и безопасность!


			session.removeAttribute("authFiltered");

			if (shortSession == null) { //save cookies only if not checked short session
				final Cookie cookie = new Cookie("userId", String.valueOf(customer.getId()));
				resp.addCookie(cookie);
			}
			userInfoSessionStorage.setUserId(customer.getId());
			userInfoSessionStorage.setUserName(customer.getName());

			if (returnUrl.isEmpty() || returnUrl.equals("/logout")) {
				returnUrl = "/wall/id" + customer.getId();
			}
			return new ModelAndView("redirect:" + returnUrl, null);
		} else { // if user was not found - return to login page
			resp.setCharacterEncoding("UTF-8");
			req.setAttribute("wrong", Boolean.TRUE); // передать через errors?
			Map<String, String> errors = new HashMap<>();
			errors.put("password", "incorrect email or password");
			errors.put("returnUrl", returnUrl);
			return new ModelAndView("/", errors);
		}*/

		return null;
	}
}
