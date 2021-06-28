package com.louisblogs.louismall.order.config;

import com.louisblogs.louismall.order.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 17:04
 */

@Configuration
public class OrderWebConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//拦截哪个拦截器的所有请求
		registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
	}
}
