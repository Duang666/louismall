package com.louisblogs.louismall.order.dao;

import com.louisblogs.louismall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:16:03
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
