package com.louisblogs.louismall.order.feign;

import com.louisblogs.louismall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 18:42
 */

@FeignClient("louismall-cart")//这个远程服务
public interface CartFeignService {

	@GetMapping("/currentUserItems")
	List<OrderItemVo> currentUserItems();
}
