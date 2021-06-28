package com.louisblogs.louismall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/27 11:39
 */

@Data
public class WareSkuLockVo {

	private String orderSn;     //订单号

	private List<OrderItemVo> locks;    //需要锁住的所有库存信息

}
