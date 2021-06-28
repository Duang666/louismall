package com.louisblogs.louismall.order.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author ：luqi
 * @description：封装订单提交数据
 * @date ：2021/6/27 2:15
 */

@ToString
@Data
public class OrderSubmitVo {

	private Long addrId;
	//支付方式
	private Integer payType;

	// TODO 无需提交购买的商品，去购物车在获取一遍

	// TODO 优惠、发票....

	//防重令牌
	private String orderToken;

	//应付价格 校验价格
	BigDecimal payPrice;

	//订单备注 要不要辣的
	private String node;

	// TODO 用户相关信息，直接去session取出登录用户

}
