package com.louisblogs.common.to.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/30 9:48
 */

@Data
public class StockDetailTo implements Serializable {

	/**
	 * id
	 */
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * sku_name
	 */
	private String skuName;
	/**
	 * 购买个数
	 */
	private Integer skuNum;
	/**
	 * 工作单id
	 */
	private Long taskId;
	/**
	 * 仓库id
	 */
	private Long wareId;
	/**
	 * 1-已锁定  2-已解锁  3-扣减
	 */
	private Integer lockStatus;
}
