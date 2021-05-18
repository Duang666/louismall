package com.louisblogs.louismall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.louisblogs.louismall.member.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallMemberApplication.class, args);
	}

}
