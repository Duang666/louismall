package com.louisblogs.common.exception;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/28 15:11
 */

public class NoStockException extends RuntimeException {


	private Long skuId;
	public NoStockException(Long skuId){
		super("商品id："+skuId+"；没有足够的库存了");
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public NoStockException(String msg1) {
		super(msg1);
	}
}
