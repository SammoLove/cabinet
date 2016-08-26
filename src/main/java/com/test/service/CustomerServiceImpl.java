package com.test.service;

import com.test.model.Customer;
import com.test.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customerRepository = customerRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void encodePasswordsAndSave(Customer customer) {
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customer.setRole(1); //for a while until there is only one role
		customerRepository.save(customer);
	}

	/*@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}*/

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		return getFromCustomer(customer);
	}

	public UserDetails getFromCustomer(Customer customer) {
		return new User(customer.getEmail(),
				customer.getPassword(),
				getAuthorities(customer.getRole()));
	}

	private List<GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = new ArrayList<>();
		if (role == 0) {
			authList.add(new SimpleGrantedAuthority("ROLE_GUEST"));
		} else if (role == 1) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else if (role == 2) {
			authList.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
		}
		return authList;
	}
}