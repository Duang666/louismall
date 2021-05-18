package com.louisblogs.louismall.coupon.dao;

import com.louisblogs.louismall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:43:24
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
