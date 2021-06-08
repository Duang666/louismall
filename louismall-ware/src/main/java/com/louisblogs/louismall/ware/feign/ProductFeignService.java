package com.louisblogs.louismall.ware.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/8 14:58
 */

@FeignClient("louismall-product")
public interface ProductFeignService {

	@RequestMapping("/product/skuinfo/info/{skuId}")
	//@RequiresPermissions("product:skuinfo:info")
	public R info(@PathVariable("skuId") Long skuId);

}
