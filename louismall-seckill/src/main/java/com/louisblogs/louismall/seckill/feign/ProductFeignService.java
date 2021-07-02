package com.louisblogs.louismall.seckill.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/1 21:16
 */

@FeignClient("louismall-product")//这个远程服务
public interface ProductFeignService {

	/**
	 * 信息（原）
	 * 远程服务gulimall-seckill用了一次
	 */
	@RequestMapping("/product/skuinfo/info/{skuId}")
	R getSkuInfo(@PathVariable("skuId") Long skuId);
}