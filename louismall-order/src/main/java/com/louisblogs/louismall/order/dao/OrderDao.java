package com.louisblogs.louismall.order.dao;

import com.louisblogs.louismall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:16:03
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

	void updateOrderStatus(String outTradeNo, Integer code);
}
