package com.test.service;

import com.test.model.Customer;

public interface LoginService {
	boolean ensureCustomer();

	Customer checkPassword(String email, String password);

	String findLoggedinUser();

	void autologin(String username, String password);
}