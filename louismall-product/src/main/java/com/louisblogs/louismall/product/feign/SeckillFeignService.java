package com.louisblogs.louismall.product.feign;

import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.product.feign.fallback.SeckillFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/2 15:16
 */

@FeignClient(value = "louismall-seckill", fallback = SeckillFeignServiceFallBack.class)//这个远程服务
public interface SeckillFeignService {

	/**
	 * 来自远程服务gulimall-product
	 * 获取当前sku的秒杀预告信息
	 */
	@GetMapping("/sku/seckill/{skuId}")
	R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}
