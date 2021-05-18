package com.louisblogs.louismall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.coupon.entity.CouponSpuRelationEntity;

import java.util.Map;

/**
 * 优惠券与产品关联
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:43:24
 */
public interface CouponSpuRelationService extends IService<CouponSpuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

