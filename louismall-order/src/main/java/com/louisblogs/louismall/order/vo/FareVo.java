package com.louisblogs.louismall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 22:46
 */

@Data
public class FareVo {
	private MemberAddressVo address;
	private BigDecimal fare;
}
