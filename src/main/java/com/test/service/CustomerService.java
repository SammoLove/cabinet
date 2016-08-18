package com.test.service;

import com.test.entity.Customer;

public interface CustomerService {
	void encodePasswordsAndSave(Customer customer);

	Customer findByEmail(String email);
}