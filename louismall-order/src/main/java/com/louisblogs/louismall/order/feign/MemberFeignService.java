package com.louisblogs.louismall.order.feign;

import com.louisblogs.louismall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 17:53
 */

@FeignClient("louismall-member")
public interface MemberFeignService {

	@GetMapping("/member/memberreceiveaddress/{memberId}/addresses")
	List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);

}
