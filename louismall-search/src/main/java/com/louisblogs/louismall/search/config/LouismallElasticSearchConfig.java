package com.louisblogs.louismall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/9 10:45
 */

/**
 * 1、导入依赖
 * 2、编写配置，给容器中注入一个RestHighLevelClient
 * 3、参照API https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.5/java-rest-high-getting-started-initialization.html
 */
@Configuration
public class LouismallElasticSearchConfig {

	public static final RequestOptions COMMON_OPTIONS;
	static {
		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//		builder.addHeader("Authorization", "Bearer " + TOKEN);
//		builder.setHttpAsyncResponseConsumerFactory(
//				new HttpAsyncResponseConsumerFactory
//						.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
		COMMON_OPTIONS = builder.build();
	}

	@Bean
	public RestHighLevelClient esRestClient() {

			RestClientBuilder builder = RestClient.builder(
					new HttpHost("192.168.56.10", 9200, "http")
//                        new HttpHost("localhost", 9201, "http")
			);
			RestHighLevelClient client = new RestHighLevelClient(builder);
			return client;
	}

}
