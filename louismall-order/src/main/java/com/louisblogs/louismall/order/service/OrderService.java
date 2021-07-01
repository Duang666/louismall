package com.louisblogs.louismall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.order.entity.OrderEntity;
import com.louisblogs.louismall.order.vo.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:16:03
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 订单确认页返回需要用的数据
	 * @return
	 */
	OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

	/**
	 * 下单
	 * @param vo
	 * @return
	 */
	SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

	OrderEntity getOrderByOrderSn(String orderSn);

	void closeOrder(OrderEntity entity);

	PayVo getPayOrder(String orderSn);

	PageUtils queryPageWithItem(Map<String, Object> params);

	String handlePayResult(PayAsyncVo payAsyncVo);
}

