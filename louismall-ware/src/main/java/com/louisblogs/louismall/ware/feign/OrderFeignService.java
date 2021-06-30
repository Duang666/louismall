package com.louisblogs.louismall.ware.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/30 10:35
 */

@FeignClient("louismall-order")//这个远程服务
public interface OrderFeignService {

	/**
	 * 按照订单号查询订单
	 */
	@GetMapping("/order/order/status/{orderSn}")
	R getOrderStatus(@PathVariable("orderSn") String orderSn);

}
