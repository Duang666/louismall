package com.louisblogs.louismall.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.louisblogs.common.exception.BizCodeEnume;
import com.louisblogs.louismall.member.exception.PhoneExistException;
import com.louisblogs.louismall.member.exception.UsernameExistException;
import com.louisblogs.louismall.member.vo.MemberLoginVo;
import com.louisblogs.louismall.member.vo.MemberRegistVo;
import com.louisblogs.louismall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.louisblogs.louismall.member.entity.MemberEntity;
import com.louisblogs.louismall.member.service.MemberService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.R;


/**
 * 会员
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 11:04:55
 */
@RestController
@RequestMapping("member/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/oauth2/login")
	public R oauthLogin(@RequestBody SocialUser socialUser) throws Exception {
		MemberEntity entity = memberService.login(socialUser);
		//TODO 1、登录成功处理
		if (entity!=null){
			return R.ok().setData(entity);
		}else {
			return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
		}
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("member:member:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = memberService.queryPage(params);

		return R.ok().put("page", page);
	}

	@PostMapping("/login")
	public R login(@RequestBody MemberLoginVo vo){
		MemberEntity entity = memberService.login(vo);
		if (entity!=null){
			//TODO 1、登录成功处理
			return R.ok();
		}else {
			return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVAILD_EXCEPTION.getMsg());
		}
	}

	/**
	 * 保存会员信息
	 */
	@PostMapping("/regist")
	public R regist(@RequestBody MemberRegistVo vo) {
		try {
			memberService.regist(vo);
		} catch (PhoneExistException e) {
			return R.error(BizCodeEnume.PHONE_EXIST_EXCEPTION.getCode(),BizCodeEnume.PHONE_EXIST_EXCEPTION.getMsg());
		} catch (UsernameExistException e) {
			return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(),BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
		}
		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("member:member:info")
	public R info(@PathVariable("id") Long id) {
		MemberEntity member = memberService.getById(id);

		return R.ok().put("member", member);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("member:member:save")
	public R save(@RequestBody MemberEntity member) {
		memberService.save(member);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("member:member:update")
	public R update(@RequestBody MemberEntity member) {
		memberService.updateById(member);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("member:member:delete")
	public R delete(@RequestBody Long[] ids) {
		memberService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
