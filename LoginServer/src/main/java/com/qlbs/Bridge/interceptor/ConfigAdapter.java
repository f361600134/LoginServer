package com.qlbs.Bridge.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigAdapter extends WebMvcConfigurerAdapter {

	@Bean
	public LoginInterceptor localInterceptor() {
		return new LoginInterceptor();
	}

	/**
	 * 配置需要过滤的方法,主要登陆过滤
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new
		// LoginInterceptor()).addPathPatterns("/*/login");
		registry.addInterceptor(localInterceptor()).addPathPatterns("/*/login").addPathPatterns("/*/login/");
		super.addInterceptors(registry);
	}
}