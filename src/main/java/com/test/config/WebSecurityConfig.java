package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableGlobalAuthentication
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;

	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/", "/css/**", "/js/**", "/fonts/**").permitAll()
				.antMatchers("/cabinet/**").access("hasRole('ROLE_CUSTOMER')")
				//.anyRequest().authenticated() ?? maybe will be just second line
				.and()
				.formLogin()
				.loginPage("/") //was ?error=PWRequired
				.loginProcessingUrl("/login1") //todo return wo 1
				.successForwardUrl("/cabinet?info=Welcome")
				//.failureUrl("/?error=WrongPW")
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
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setSessionAttributeName("_csrf");
		return repository;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	public SaltSource saltSource() {
		return new SaltSource() {
			@Override
			public Object getSalt(UserDetails userDetails) {
				return null;
			}
		}
	}*/
}