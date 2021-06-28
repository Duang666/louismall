package com.louisblogs.louismall.order.vo;

import com.louisblogs.louismall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/27 2:22
 */

@Data
public class SubmitOrderResponseVo {

	//下单成功返回这个实体
	private OrderEntity order;
	//下单错误给一个状态码 0:成功
	private Integer code;

}
