package com.louisblogs.louismall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:16:03
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

