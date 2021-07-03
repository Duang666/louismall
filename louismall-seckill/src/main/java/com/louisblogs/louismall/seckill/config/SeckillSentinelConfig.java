package com.louisblogs.louismall.seckill.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.louisblogs.common.exception.BizCodeEnume;
import com.louisblogs.common.utils.R;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/2 20:23
 */

@Configuration
public class SeckillSentinelConfig {
	public SeckillSentinelConfig() {
		WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
			@Override
			public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
				R r = R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(), BizCodeEnume.TO_MANY_REQUEST.getMsg());
				//解决response乱码
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write(JSON.toJSONString(r));
			}
		});
	}
}