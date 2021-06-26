package com.louisblogs.louismall.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/25 2:01
 */

//开启Spring Session功能 redis
@EnableRedisHttpSession
@Configuration
public class LouismallSessionConfig {

	/**
	 * 自定义session作用域：整个网站
	 * 使用一样的session配置，能保证全网站共享一样的session
	 */
	@Bean
	public CookieSerializer cookieSerializer() {

		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();

		cookieSerializer.setDomainName("louismall.com");
		cookieSerializer.setCookieName("LOUISSESSION");

		return cookieSerializer;
	}

	/**
	 * 序列化机制
	 */
	@Bean
	public RedisSerializer<Object> springSessionDefaultRedisSerializer(){

		return new GenericJackson2JsonRedisSerializer();
	}

}
