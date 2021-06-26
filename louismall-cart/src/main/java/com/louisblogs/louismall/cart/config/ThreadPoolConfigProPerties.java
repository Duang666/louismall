package com.louisblogs.louismall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 9:45
 */

@ConfigurationProperties("louismall.thread")
@Component
@Data
public class ThreadPoolConfigProPerties {

	private Integer coreSize;   //核心大小
	private Integer maxSize;    //最大大小
	private Integer keepAliveTime;  //休眠时长

}
