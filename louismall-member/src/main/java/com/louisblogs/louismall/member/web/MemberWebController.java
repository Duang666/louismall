package com.louisblogs.louismall.member.web;

import com.alibaba.fastjson.JSON;
import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.member.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/1 8:56
 */

@Controller
public class MemberWebController {

	@Autowired
	OrderFeignService orderFeignService;

	/**
	 * 我的订单
	 */
	@GetMapping("/memberOrder.html")
	public String memberOrderPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Map<String, R> map) {
		//获取到支付宝给我们传来的所有请求数据:
		//request.验证签名，如果正确可以去修改。

		//查出当前登录的用户的所有订单列表数据
		Map<String, Object> page = new HashMap<>();
		page.put("page", pageNum.toString());
		R r = orderFeignService.listWithItem(page);
		System.out.println(JSON.toJSONString(r));
		map.put("orders", r);
		return "orderList";
	}
}
