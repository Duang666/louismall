package com.louisblogs.louismall.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ：luqi
 * @description：订单确认页需要用的数据
 * @date ：2021/6/26 17:34
 */

//@Data
public class OrderConfirmVo {


	//收货地址列表    `ums_member_receive_address`
	@Setter @Getter
	List<MemberAddressVo> address;

	//所有选中的购物项
	@Setter @Getter
	List<OrderItemVo> items;

	//TODO 发票记录....

	//TODO 优惠信息....
	//优惠券 积分
	@Setter @Getter
	private Integer integration;

	//订单的防重令牌
	@Setter @Getter
	String orderToken;

	@Setter @Getter
	Map<Long, Boolean> stocks;

	//总件数
	public Integer getCount() {
		Integer sum = 0;
		if (items != null) {
			for (OrderItemVo item : items) {
				sum += item.getCount();
			}
		}
		return sum;
	}

	//订单总额 需要计算
//	private BigDecimal total;
	public BigDecimal getTotal() {
		BigDecimal sum = new BigDecimal("0");
		if (items != null) {
			for (OrderItemVo item : items) {
				BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
				sum = sum.add(multiply);
			}
		}
		return sum;
	}

	//应付价格 需要计算
//	private BigDecimal payPrice;
	public BigDecimal getPayPrice() {
		return getTotal();
	}

}
