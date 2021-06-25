package com.louisblogs.louismall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.louisblogs.common.constant.AuthServerConstant;
import com.louisblogs.common.exception.BizCodeEnume;
import com.louisblogs.common.utils.R;
import com.louisblogs.common.vo.MemberRespVo;
import com.louisblogs.louismall.auth.feign.MemberFeignService;
import com.louisblogs.louismall.auth.feign.ThirdPartFeignService;
import com.louisblogs.louismall.auth.vo.UserLoginVo;
import com.louisblogs.louismall.auth.vo.UserRegistVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 11:19
 */

@Controller
public class LoginController {

	@Autowired
	ThirdPartFeignService thirdPartFeignService;

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	MemberFeignService memberFeignService;

	/**
	 * 发送一个请求直接跳转到一个页面
	 * SringMVC viewcontroller: 将请求和页面映射过来
	 */
	@ResponseBody
	@GetMapping("/sms/sendcode")
	public R sendCode(@RequestParam("phone") String phone) {

		//TODO 1、接口防刷
		String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
		if (!StringUtils.isEmpty(redisCode)) {
			long l = Long.parseLong(redisCode.split("_")[1]);
			if (System.currentTimeMillis() - l < 60000) {
				//60秒内不能再发
				return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
			}
		}

		//2、验证码的再次校验。redis。存key-phone,value-code
		String code = UUID.randomUUID().toString().substring(0, 5);
		String subString = code + "_" + System.currentTimeMillis();
		//redis缓存验证码，防止同一个phone在60s内再次发送验证码
		redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, subString, 10, TimeUnit.MINUTES);

		thirdPartFeignService.sendCode(phone, code);
		return R.ok();
	}


	// TODO 重定向携带数据，利用session原理。将数据放在session中。只要跳到下一个页面取出这个数据以后，session里面的数据就会删掉
	// TODO 1、分布式下的session问题

	/**
	 *  RedirectAttributes redirectAttributes: 模拟重定向携带数据
	 *      * @Valid BindingResult result 都是JSR303校验
	 *      * RedirectAttributes 重定向携带数据 代替Model
	 */
	@PostMapping("/regist")
	public String regist(@Valid UserRegistVo vo, BindingResult result,
	                     RedirectAttributes redirectAttributes,
	                     HttpSession session) {
		//有问题
		if (result.hasErrors()) {
//			Map<String, String> errors1 = result.getFieldErrors().stream().collect(Collectors.toMap(fieldError -> {
//                return fieldError.getField();//key
//            }, fieldError -> {
//                return fieldError.getDefaultMessage();//value
//            }));
			Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

			//Request method 'POST ' not supported
			//用户注册->/regist[post---->转发/reg. html (路径映射默认都是get方式访问的。)
//			model.addAttribute("errors", errors);
			redirectAttributes.addFlashAttribute("errors", errors);

			//后端校验出错，转发到注册页
			return "redirect:http://auth.louismall.com/register.html";
		}

		//没问题 真正的注册， 调用远程服务注册
		//1 校验验证码
		String code = vo.getCode();
		String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
		if (!StringUtils.isEmpty(redisCode)) {
			//说明redis存了验证码
			if (code.equals(redisCode.split("_")[0])) {
				//说明redis验证码 = 前端传过来的 可以远程注册
				//先删除验证码 令牌机制
				redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
				//验证码通过。//真正注册。 调用远程服务进行注册
				R r = memberFeignService.regist(vo);
				if (r.getCode() == 0) {
					//注册成功后回到首页，或者回到登录页
					return "redirect:http://auth.louismall.com/login.html";
				}else {
					//出现异常 或者 失败
					Map<String, String> errors = new HashMap<>();
					errors.put("msg", r.getData("msg", new TypeReference<String>(){}));//R错误消息都在msg里
					redirectAttributes.addFlashAttribute("errors", errors);
					return "redirect:http://auth.louismall.com/register.html";
				}

			}else {
				//说明验证码不对
				Map<String, String> errors = new HashMap<>();
				errors.put("code", "验证码匹配不上");
				redirectAttributes.addFlashAttribute("errors", errors);
				return "redirect:http://auth.louismall.com/register.html";
			}
		}else {
			//说明验证码没了，过期了
			Map<String, String> errors = new HashMap<>();
			errors.put("code", "redis没有验证码");
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:http://auth.louismall.com/register.html";
		}

//		//注册成功回到首页，回到登录页
//		return "redirect:http://auth.louismall.com/login.html";
	}

	@PostMapping("/login")
	public String login(UserLoginVo vo,RedirectAttributes redirectAttributes,HttpSession session){

		//远程登录
		R login = memberFeignService.login(vo);
		if (login.getCode()==0){
			//远程登录成功，将远程服务返回的entity放入session中
			MemberRespVo memberRespVo = login.getData("data", new TypeReference<MemberRespVo>(){});
			session.setAttribute(AuthServerConstant.LOGIN_USER, memberRespVo);
			return "redirect:http://louismall.com";
		}else {
			Map<String,String> errors = new HashMap<>();
			errors.put("msg",login.getData("msg",new TypeReference<String>(){}));
			redirectAttributes.addFlashAttribute("errors",errors);
			return "redirect:http://auth.louismall.com/login.html";
		}
	}

	/**
	 * 处理已经登录的用户，误操作到登录页面
	 */
	@GetMapping("/login.html")
	public String loginPage(HttpSession session) {

		//判断用户是否已经登录
		Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
		if (attribute == null) {
			//没有登录过 可以跳转到登录页面
			return "login";
		}else {
			//已经登录，禁止跳转到登录页，跳转首页即可
			return "redirect:http://louismall.com";
		}
	}

}
