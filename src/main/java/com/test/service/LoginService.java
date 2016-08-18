package com.test.service;

import com.test.entity.Customer;

public interface LoginService {
	Customer checkPassword(String email, String password);

	String findLoggedInUsername();

	void autologin(String username, String password);
}