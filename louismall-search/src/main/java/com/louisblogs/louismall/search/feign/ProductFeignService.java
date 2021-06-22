package com.louisblogs.louismall.search.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/21 22:06
 */

@FeignClient("louismall-product")
public interface ProductFeignService {

	@GetMapping("/product/attr/info/{attrId}")
	public R attrInfo(@PathVariable("attrId") Long attrId);

	@GetMapping("/product/brand/infos")
	public R BrandsInfo(@RequestParam("brandIds") List<Long> brandIds);

}
