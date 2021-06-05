package com.louisblogs.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/4 22:36
 */

@Data
public class SkuReductionTo {

	private Long skuId;
	private int fullCount;
	private BigDecimal discount;
	private int countStatus;
	private BigDecimal fullPrice;
	private BigDecimal reducePrice;
	private int priceStatus;
	private List<MemberPrice> memberPrice;

}
