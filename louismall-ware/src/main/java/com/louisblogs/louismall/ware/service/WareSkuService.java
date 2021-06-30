package com.louisblogs.louismall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.to.SkuHasStockVo;
import com.louisblogs.common.to.mq.OrderTo;
import com.louisblogs.common.to.mq.StockLockedTo;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.ware.entity.WareSkuEntity;
import com.louisblogs.louismall.ware.vo.LockStockResult;
import com.louisblogs.louismall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:29:31
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void addStock(Long skuId, Long wareId, Integer skuNum);

	List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

	Boolean  orderLockStock(WareSkuLockVo vo);

	void unLockStock(StockLockedTo to);

	void unLockStock(OrderTo orderTo);
}

