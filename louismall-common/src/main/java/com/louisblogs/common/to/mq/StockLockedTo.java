package com.louisblogs.common.to.mq;

import lombok.Data;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/30 9:47
 */

@Data
public class StockLockedTo {

	//库存工作单id
	private Long id;

	//工作单详情的所有id
	private StockDetailTo detailTo;

}
