package com.louisblogs.louismall.member.dao;

import com.louisblogs.louismall.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 11:04:54
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
