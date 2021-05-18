package com.louisblogs.louismall.member.dao;

import com.louisblogs.louismall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 11:04:55
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
