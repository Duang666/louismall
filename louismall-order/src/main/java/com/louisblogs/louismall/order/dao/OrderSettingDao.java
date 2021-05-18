package com.louisblogs.louismall.order.dao;

import com.louisblogs.louismall.order.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:16:02
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
