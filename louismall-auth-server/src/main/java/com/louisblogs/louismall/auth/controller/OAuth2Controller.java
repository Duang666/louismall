package com.louisblogs.louismall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.louisblogs.common.utils.HttpUtils;
import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.auth.feign.MemberFeignService;
import com.louisblogs.louismall.auth.vo.MemberRespVo;
import com.louisblogs.louismall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：luqi
 * @description：处理社交登录请求
 * @date ：2021/6/24 10:56
 */

@Slf4j
@Controller
public class OAuth2Controller {

	@Autowired
	MemberFeignService memberFeignService;

	@GetMapping("/oauth2.0/weibo/success")
	public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {

		Map<String, String> map = new HashMap<>();
		map.put("client_id", "2103634004");//和login.html的要保持一致
		map.put("client_secret", "735ca4c8bf0201355b200388f1b2b718");
		map.put("grant_type", "authorization_code");
		map.put("redirect_uri", "http://auth.louismall.com/oauth2.0/weibo/success");
		map.put("code", code);
		//1 根据 code 换取 access_token 能获取则成功
		HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), map, new HashMap<>());

		//处理返回的 response 这个 json
		if (response.getStatusLine().getStatusCode() == 200) {
			//成功获取了access_token JSON逆转为对象
			String json = EntityUtils.toString(response.getEntity());
			SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
			//得知道是哪个社交用户登录的
			//1） 第一次用 微博 进行 社交登录=>注册，进行一对一绑定注册到远程服务的数据库
			//    当前用户如果是第一次进网站， 自动注册进来(为当前社交用户生成一个会员信息账号，以后这个社交账号就对应指定的会员)
			//2） 多次用 微博 进行 社交登录=>登录
			//    登录或者注册这个社交用户
			//由远程服务来做判断
			R oauthLogin = memberFeignService.oauthLogin(socialUser);
			if (oauthLogin.getCode() == 0) {
				MemberRespVo data = oauthLogin.getData("data", new TypeReference<MemberRespVo>(){});
				log.info("社交登录成功，用户信息为：{}" + data.toString());
				//1 第一次使用SESSION 命令浏览器保存JSESSIONID的cookie
				//以后浏览器访问哪个网站就会带上这个网站的cookie
				//子域之间：gulimall.com auth.guliamll.com member.gulimall.com
				//发卡发的时候(指定域名为父域名)，即使是子系统发的卡，也能让父系统使用
				//TODO 1 默认发的令牌 session=asdfg 作用域是当前域：解决子域session共享问题
				//TODO 2 希望使用json序列化对象到redis中
				//远程登录成功，将远程服务返回的entity放入session中
//				session.setAttribute(AuthServiceConstant.LOGIN_USER, memberResVo);
//                servletResponse.addCookie(new Cookie("JSESSIONID", "dada").setDomain());
				//登录成功 -> 跳转首页
				return "redirect:http://louismall.com";
			}else {
				//失败 重新登录
				return "redirect:http://auth.louismall.com/login.html";
			}

		}else {
			//没有获取了access_token 登录失败 返回到登录页
			return "redirect:http://auth.louismall.com/login.html";
		}
	}
}
