package com.test.repository;

import com.test.model.Customer;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	Customer findByEmail(String email);
}