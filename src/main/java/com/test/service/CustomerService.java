package com.test.service;

import com.test.model.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerService extends UserDetailsService {
	void encodePasswordsAndSave(Customer customer);

	//Customer findByEmail(String email);

	@Override
	UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}