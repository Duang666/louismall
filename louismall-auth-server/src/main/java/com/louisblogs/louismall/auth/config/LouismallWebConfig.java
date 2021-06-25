package com.louisblogs.louismall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 17:53
 */

@Configuration
public class LouismallWebConfig implements WebMvcConfigurer {

	/**
	 * 视图映射
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry){
//        registry.addViewController("/login.html").setViewName("login");
		registry.addViewController("/register.html").setViewName("register");
	}

}
