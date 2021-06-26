package com.louisblogs.louismall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("com.louisblogs.louismall.order.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class LouismallOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallOrderApplication.class, args);
	}

}
