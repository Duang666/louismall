package com.louisblogs.common.to.mq;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/2 17:26
 */

@Data
public class SeckillOrderTo {

	private String OrderSn;
	/**
	 * 活动场次id
	 */
	private Long promotionSessionId;
	/**
	 * 商品id
	 */
	private Long skuId;
	/**
	 * 秒杀价格
	 */
	private BigDecimal seckillPrice;
	/**
	 * 秒杀总量
	 */
	private Integer num;

	//会员id
	private Long memberId;

}
