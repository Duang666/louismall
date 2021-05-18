package com.louisblogs.louismall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.louisblogs.louismall.ware.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallWareApplication.class, args);
	}

}
