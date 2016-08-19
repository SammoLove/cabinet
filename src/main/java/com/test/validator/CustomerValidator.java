package com.test.validator;

import com.test.entity.Customer;
import com.test.service.CustomerService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class CustomerValidator implements org.springframework.validation.Validator {
	private final CustomerService customerService;

	public CustomerValidator(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Customer.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Customer customer = (Customer) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email EmptyOrWhitespace!");
		if (customer.getEmail().length() < 5 || customer.getEmail().length() > 32) {
			errors.rejectValue("email", "Abnormal an email size!");
		}
		if (customerService.findByEmail(customer.getEmail()) != null) {
			errors.rejectValue("email", "userForm.email.duplicate");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password EmptyOrWhitespace!");
		if (customer.getPassword().length() < 6 || customer.getPassword().length() > 32) {
			errors.rejectValue("password", "Password size <6 or >32!");
		}

		if (!customer.getPasswordConfirm().equals(customer.getPassword())) {
			errors.rejectValue("passwordConfirm", "Passwords mismatch!");
		}
	}
}