package com.louisblogs.louismall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.louisblogs.louismall.coupon.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallCouponApplication.class, args);
	}

}
