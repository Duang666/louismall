package com.louisblogs.louismall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：luqi
 * @description：整个购物车
 * @date ：2021/6/25 20:09
 */
//@Data 不用的原因
//需要计算的属性必须重新get()的方法，这样才能保证每一次属性都会进行重新计算
public class Cart {

	List<CartItem> items;
	//全部sku的总数 商品数量
	private Integer countNum;
	//商品类型数量
	private Integer countType;
	//所有sku总价   商品总价
	private BigDecimal totalAmount;
	//优惠价格
	private BigDecimal reduce = new BigDecimal("0.00");

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public Integer getCountNum() {
		int count = 0;
		if (items != null && items.size() > 0) {
			for (CartItem item : items) {
				count += item.getCount();
			}
		}
		return count;
	}

	public Integer getCountType() {
		int count = 0;
		if (items != null && items.size() > 0) {
			for (CartItem item : items) {
				count += 1;
			}
		}
		return count;
	}

	public BigDecimal getTotalAmount() {
		BigDecimal amount = new BigDecimal("0");
		//1 计算购物项的总价
		if (items != null && items.size() > 0) {
			for (CartItem item : items) {
				if (item.getCheck()) {
					amount = amount.add(item.getTotalPrice());
				}
			}
		}
		//2 减掉优惠总价
		return amount.subtract(getReduce());
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getReduce() {
		return reduce;
	}

	public void setReduce(BigDecimal reduce) {
		this.reduce = reduce;
	}
}
