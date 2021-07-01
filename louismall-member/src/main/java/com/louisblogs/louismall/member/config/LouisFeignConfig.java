package com.louisblogs.louismall.member.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 19:30
 */

@Configuration
public class LouisFeignConfig {

	@Bean("requestInterceptor")
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				//1、RequestContextHolder 拿到刚进来的这个请求
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				System.out.println("requestInterceptor线程...."+Thread.currentThread().getId());
				if (attributes != null) {
					HttpServletRequest request = attributes.getRequest();
					if (request != null) {
						//同步请求头数据 主要同步Cookie
						String cookie = request.getHeader("Cookie");
						//给新请求同步的老请求Cookie
						template.header("Cookie", cookie);
					}
				}
			}
		};
	}

}
