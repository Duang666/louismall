package com.louisblogs.louismall.ware.vo;

import lombok.Data;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/27 11:46
 */

@Data
public class LockStockResult {

	private Long skuId;

	private Integer num;

	private boolean locked;

}
