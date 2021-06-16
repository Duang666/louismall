package com.louisblogs.louismall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/16 8:57
 */

@Configuration
public class MyRedissonConfig {

	/**
	 * 所有对Redisson的使用都是通过RedissonClient对象
	 * @return
	 * @throws IOException
	 */
	@Bean(destroyMethod="shutdown")
	public RedissonClient redisson() throws IOException {
		//1、创建配置
		Config config = new Config();
		config.useSingleServer().setAddress("redis://192.168.56.10:6379");

		//2、根据Config创建出RedissonClient实例
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient;
	}

}
