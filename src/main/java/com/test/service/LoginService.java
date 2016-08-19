package com.test.service;

import com.test.entity.Customer;

public interface LoginService {
	Customer checkPassword(String email, String password);

	String findLoggedinUser();

	void autologin(String username, String password);
}