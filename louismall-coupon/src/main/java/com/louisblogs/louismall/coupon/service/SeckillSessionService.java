package com.louisblogs.louismall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.coupon.entity.SeckillSessionEntity;

import java.util.List;
import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:43:23
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<SeckillSessionEntity> getLatest3DaysSession();
}

