package com.louisblogs.louismall.product.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 9:37
 */

//@EnableConfigurationProperties(ThreadPoolConfigProPerties.class)
@Configuration
public class MyThreadConfig {

	@Bean
	public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProPerties pool){
		return new ThreadPoolExecutor(pool.getCoreSize(), pool.getMaxSize(), pool.getKeepAliveTime(),
				TimeUnit.SECONDS,new LinkedBlockingDeque<>(100000),
				Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
	}

}
