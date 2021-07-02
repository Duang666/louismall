package com.louisblogs.louismall.seckill.config;

import com.louisblogs.louismall.seckill.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/2 17:17
 */

@Configuration
public class SeckillWebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		//拦截哪个拦截器的所有请求
		registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
	}
}