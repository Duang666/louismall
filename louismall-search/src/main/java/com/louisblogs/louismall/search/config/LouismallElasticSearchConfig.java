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

	@Bean
	public RestHighLevelClient esRestClient() {

		RestClientBuilder builder = null;
		//final String hostname, final int port, final String scheme
		builder = RestClient.builder(new HttpHost("101.200.192.10", 9200, "http"));
		RestHighLevelClient client = new RestHighLevelClient(builder);

//		RestHighLevelClient client = new RestHighLevelClient(
//				RestClient.builder(
//						new HttpHost("101.200.192.10", 9200, "http")));
		return client;
	}

	public static final RequestOptions COMMON_OPTIONS;
	static {
		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//		builder.addHeader("Authorization", "Bearer " + TOKEN);
//		builder.setHttpAsyncResponseConsumerFactory(
//				new HttpAsyncResponseConsumerFactory
//						.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
		COMMON_OPTIONS = builder.build();
	}

}
