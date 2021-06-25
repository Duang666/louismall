package com.louisblogs.louismall.auth.feign;

import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.auth.vo.SocialUser;
import com.louisblogs.louismall.auth.vo.UserLoginVo;
import com.louisblogs.louismall.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/24 0:45
 */

@FeignClient("louismall-member")
public interface MemberFeignService {

	@PostMapping("/member/member/regist")
	R regist(@RequestBody UserRegistVo vo);

	@PostMapping("/member/member/login")
	R login(@RequestBody UserLoginVo vo);

	@PostMapping("/member/member/oauth2/login")
	R oauthLogin(@RequestBody SocialUser vo) throws Exception;

}
