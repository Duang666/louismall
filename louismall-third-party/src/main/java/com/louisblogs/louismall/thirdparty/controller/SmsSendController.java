package com.louisblogs.louismall.thirdparty.controller;

import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 19:44
 */

@RestController
@RequestMapping("/sms")
public class SmsSendController {

	@Autowired
	SmsComponent smsComponent;

	/**
	 * 提供给别的服务调用的
	 */
	@GetMapping("/sendcode")
	public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

		smsComponent.sendSmsCode(phone, code);

		return R.ok();
	}

}
