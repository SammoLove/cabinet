package com.test.service;

import com.test.model.Customer;
import com.test.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
	private final CustomerRepository customerRepository;
	private final UserDetailsService userDetailsService;
	private AuthenticationManager authenticationManager;


	@Autowired
	public LoginServiceImpl(CustomerRepository customerRepository, UserDetailsService userDetailsService) {
		this.customerRepository = customerRepository;
		this.userDetailsService = userDetailsService;
	}

	@Override
	//@PreAuthorize("hasRole(@roles.CUSTOMER)")
	public boolean ensureCustomer() {
		return true;
	}

	@Deprecated
	@Override
	@Transactional
	public Customer checkPassword(String email, String password) throws UsernameNotFoundException {
		final Customer customer = customerRepository.findByEmail(email);

		if (customer != null && customer.getPassword().equals(password)) {
			return customer;
		} else { //if user with such email was not found
			return null;
		}
	}

	@Override
	public String findLoggedinUser() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	@Override
	public void autologin(String email, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(authenticationToken);

		if (authenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
	}
}