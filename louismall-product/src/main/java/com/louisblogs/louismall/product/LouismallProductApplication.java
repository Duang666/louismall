package com.louisblogs.louismall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.louisblogs.louismall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallProductApplication.class, args);
	}

}
