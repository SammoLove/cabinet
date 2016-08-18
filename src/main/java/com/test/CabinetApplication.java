package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")
public class CabinetApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CabinetApplication.class, args);
		//SpringApplication.run(new Class<?>[] {CabinetApplication.class, JpaConfig.class}, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		//return application.sources(AppConfig.class);
		return application.sources(CabinetApplication.class);
	}
}