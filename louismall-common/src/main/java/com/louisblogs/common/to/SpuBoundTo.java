package com.louisblogs.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/4 22:12
 */

@Data
public class SpuBoundTo {

	private Long spuId;
	private BigDecimal buyBounds;
	private BigDecimal growBounds;

}
