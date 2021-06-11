package com.louisblogs.louismall.product.feign;

import com.louisblogs.common.to.SkuHasStockVo;
import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 1、R设计的时候可以加上泛型
 * 2、直接返回我们想要的结果
 * 3、自己封装解析结果
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/10 10:00
 */

@FeignClient("louismall-ware")
public interface WareFeignService {

	@PostMapping("/ware/waresku/hasstock")
	R getSkuHasStock(@RequestBody List<Long> skuIds);

}
