package com.louisblogs.louismall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.member.entity.MemberEntity;
import com.louisblogs.louismall.member.exception.PhoneExistException;
import com.louisblogs.louismall.member.exception.UsernameExistException;
import com.louisblogs.louismall.member.vo.MemberRegistVo;

import java.util.Map;

/**
 * 会员
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 11:04:55
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void regist(MemberRegistVo vo);

	void checkPhoneUnique(String phone) throws PhoneExistException;

	void checkUsernameUnique(String username) throws UsernameExistException;
}

