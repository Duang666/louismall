package com.louisblogs.louismall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/5/25 20:04
 */

@EnableDiscoveryClient
@SpringBootApplication
public class LouismallThirdPartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LouismallThirdPartyApplication.class,args);
	}

}
