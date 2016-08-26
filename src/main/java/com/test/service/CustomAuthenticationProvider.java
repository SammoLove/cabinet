package com.test.service;

import com.test.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final CustomerService customerService;

	@Autowired
	public CustomAuthenticationProvider(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		Customer customer = (Customer) customerService.loadUserByUsername(username);

		if (customer == null || !customer.getEmail().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(customer.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities =
				customerService.loadUserByUsername(customer.getEmail()).getAuthorities();

		return new UsernamePasswordAuthenticationToken(customer, password, authorities);
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return false;
	}
}
