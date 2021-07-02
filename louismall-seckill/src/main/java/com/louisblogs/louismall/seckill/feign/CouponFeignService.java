package com.louisblogs.louismall.seckill.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/1 18:10
 */

@FeignClient("louismall-coupon")
public interface CouponFeignService {

	/**
	 * 给远程服务gulimall-seckill调用
	 * 扫描需要参与秒杀的活动
	 */
	@GetMapping("/coupon/seckillsession/latest3DaysSession")
	R getLatest3DaysSession();

}
