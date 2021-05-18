package com.louisblogs.louismall.product.dao;

import com.louisblogs.louismall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
