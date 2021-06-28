package com.louisblogs.louismall.order.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/27 10:40
 */

@FeignClient("louismall-product")
public interface ProductFeignService {

	@GetMapping("/product/spuinfo/skuId/{id}")
	R getSpuInfoBySkuId(@PathVariable("id") Long skuId);

}
