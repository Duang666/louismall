package com.louisblogs.louismall.seckill.service;

import com.louisblogs.louismall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/1 18:03
 */

public interface SeckillService {

	void uploadSeckillSkuLatest3Days();

	List<SeckillSkuRedisTo> getCurrentSeckillSkus();

	SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);

	String kill(String killId, String key, Integer num);

}
