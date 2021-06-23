package com.louisblogs.louismall.auth.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 19:52
 */

@FeignClient("louismall-third-party")
public interface ThirdPartFeignService {

	@GetMapping("/sms/sendcode")
	public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);

}
