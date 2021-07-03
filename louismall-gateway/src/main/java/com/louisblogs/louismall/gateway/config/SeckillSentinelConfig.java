package com.louisblogs.louismall.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.louisblogs.common.exception.BizCodeEnume;
import com.louisblogs.common.utils.R;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/2 22:14
 */

@Configuration
public class SeckillSentinelConfig {

	public SeckillSentinelConfig() {
		GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {

			/**
			 * 网关限流了请求，就会掉用此方法 Mono Flux
			 */
			@Override
			public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
				R error = R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(), BizCodeEnume.TO_MANY_REQUEST.getMsg());
				String s = JSON.toJSONString(error);
				Mono<ServerResponse> body = ServerResponse.ok().body(Mono.just(s), String.class);
				return body;
			}
		});
	}

}