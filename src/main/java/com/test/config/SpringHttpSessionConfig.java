package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Configuration
@EnableSpringHttpSession
//@ConditionalOnMissingBean(SessionRepository.class)
public class SpringHttpSessionConfig {

	/*@Bean
	public SessionRepository<ExpiringSession> sessionRepository(SessionProperties properties) {
		MapSessionRepository repository = new MapSessionRepository();
		Integer timeout = properties.getTimeout();
		if (timeout != null) {
			repository.setDefaultMaxInactiveInterval(timeout);
		}
		return repository;
	}*/

	@Bean
	public MapSessionRepository sessionRepository() {
		return new MapSessionRepository();
	}
}
