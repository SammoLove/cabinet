package com.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/main").setViewName("main");
		registry.addViewController("/").setViewName("tl/main");
		registry.addViewController("tl/main").setViewName("main");
		registry.addViewController("tl/login").setViewName("tl/login"); //todo is it need?
		registry.addViewController("tl/cabinet").setViewName("cabinet");
	}
}