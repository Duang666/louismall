package com.louisblogs.louismall.order.to;

import com.louisblogs.louismall.order.entity.OrderEntity;
import com.louisblogs.louismall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/27 9:08
 */

@Data
public class OrderCreatTo {

	private OrderEntity order;

	private List<OrderItemEntity> orderItems;

	private BigDecimal payPrice;    //订单计算的应付价格

	private BigDecimal fare;    //运费
}
