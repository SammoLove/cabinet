package com.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/", "/css*", "/js*", "/fonts*")
				.addResourceLocations("/", "/css/", "/js/", "/fonts/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("tl/main").setViewName("login"); //todo is it need?
		//registry.addViewController("tl/cabinet").setViewName("cabinet");
		//registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}