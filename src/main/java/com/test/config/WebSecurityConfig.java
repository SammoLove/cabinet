package com.test.config;

import com.test.service.CustomAuthenticationProvider;
import com.test.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
//@EnableGlobalAuthentication
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomerService customerService;
	private final CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	public WebSecurityConfig(CustomerService customerService, CustomAuthenticationProvider customAuthenticationProvider) {
		this.customerService = customerService;
		this.customAuthenticationProvider = customAuthenticationProvider;
	}

	@Autowired
	void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/", "/css/**", "/js/**", "/fonts/**").permitAll()
				.antMatchers("/cabinet/**")
				//.access("hasAnyRole('ROLE_CUSTOMER','CUSTOMER')")
				.access("hasRole('Roles.CUSTOMER')")
				//.anyRequest().authenticated() ?? maybe will be just second line
				.and()
				.formLogin()
				.loginPage("/") //?error=PWRequired
				.usernameParameter("email")
				.loginProcessingUrl("/login")
				.successForwardUrl("/cabinet?info=Welcome")
				.failureUrl("/?error=WrongPW")
				//.failureForwardUrl("/?error=PWRequired") or such
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/?info=logout")
				.permitAll();
		http.csrf()
				.csrfTokenRepository(csrfTokenRepository()); //?
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return customerService;
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setSessionAttributeName("_csrf");
		return repository;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
		//посмотриклассы имплементирующие PasswordEncoder
	}
}