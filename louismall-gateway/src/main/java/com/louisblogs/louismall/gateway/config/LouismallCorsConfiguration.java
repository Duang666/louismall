package com.louisblogs.louismall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/5/20 9:37
 */

@Configuration
public class LouismallCorsConfiguration {

	@Bean
	public CorsWebFilter corsWebFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration();

		//1、配置跨域
		corsConfiguration.addAllowedHeader("*");        //请求头
		corsConfiguration.addAllowedMethod("*");        //请求方式
		corsConfiguration.addAllowedOrigin("*");        //请求来源
		corsConfiguration.setAllowCredentials(true);    //是否允许携带cookies

		source.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsWebFilter(source);
	}

}
