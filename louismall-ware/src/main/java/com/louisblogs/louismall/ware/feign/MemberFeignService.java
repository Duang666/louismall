package com.louisblogs.louismall.ware.feign;

import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 21:51
 */

@FeignClient("louismall-member")//这个远程服务
public interface MemberFeignService {

	/**
	 * 信息
	 */
	@RequestMapping("/member/memberreceiveaddress/info/{id}")
	R addrInfo(@PathVariable("id") Long id);
}
