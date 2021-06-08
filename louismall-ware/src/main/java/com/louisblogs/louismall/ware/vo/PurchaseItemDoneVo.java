package com.louisblogs.louismall.ware.vo;

import lombok.Data;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/8 10:38
 */

@Data
public class PurchaseItemDoneVo {
	//{"itemId":1,"status":1,"reason":""}
	private Long itemId;
	private Integer status;
	private String reason;

}
