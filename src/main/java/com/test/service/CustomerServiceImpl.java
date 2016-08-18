package com.test.service;

import com.test.entity.Customer;
import com.test.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

//@Service("customerService")
@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	//private final SaltSource saltSource;
	private UserDetailsService userDetailsService;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
		this.customerRepository = customerRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		//this.saltSource = saltSource;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void encodePasswordsAndSave(Customer customer) {
		//customer.setSalt(saltSource.getSalt(userDetailsService.getUserDetailsByCustomer(customer)).toString());
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		//  + customer.getSalt() or + encode(customer.getPassword(customer.getSalt()) ??
		customerRepository.save(customer);
	}

	private UserDetails getUserDetailsByCustomer(Customer customer) {
		return new User(customer.getEmail(),
				customer.getPassword(),
				new LinkedList<>()
		);
	}

	@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
}