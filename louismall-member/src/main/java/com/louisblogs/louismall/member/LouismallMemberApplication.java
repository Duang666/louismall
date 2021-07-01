package com.louisblogs.louismall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients(basePackages = "com.louisblogs.louismall.member.feign")
@EnableRedisHttpSession
@MapperScan("com.louisblogs.louismall.member.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallMemberApplication.class, args);
	}

}
