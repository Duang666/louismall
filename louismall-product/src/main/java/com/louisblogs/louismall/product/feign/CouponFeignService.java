package com.louisblogs.louismall.product.feign;

import com.louisblogs.common.to.SkuReductionTo;
import com.louisblogs.common.to.SpuBoundTo;
import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/4 21:19
 */

@FeignClient("louismall-coupon")
public interface CouponFeignService {

	/**
	 * @param spuBoundTo
	 * @return
	 */
	@PostMapping("/coupon/spubounds/save")
	R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

	@PostMapping("/coupon/skufullreduction/saveinfo")
	R saveSkuReduction(SkuReductionTo skuReductionTo);
}
