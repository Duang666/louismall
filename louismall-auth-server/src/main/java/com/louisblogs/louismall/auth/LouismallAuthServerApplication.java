package com.louisblogs.louismall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession     //整合redis作为session存储
@EnableFeignClients(basePackages = "com.louisblogs.louismall.auth.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallAuthServerApplication.class, args);
	}

}
