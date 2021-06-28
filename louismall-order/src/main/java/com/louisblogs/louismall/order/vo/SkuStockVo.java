package com.louisblogs.louismall.order.vo;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 21:08
 */

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;

@Data
public class SkuStockVo {
	private Long skuId;

	private Boolean hasStock;
}
