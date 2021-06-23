package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.SkuInfoEntity;
import com.louisblogs.louismall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * sku信息
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveSkuInfo(SkuInfoEntity skuInfoEntity);

	//sku检索
	PageUtils queryPageByCondition(Map<String, Object> params);

	List<SkuInfoEntity> getSkuBySpuId(Long spuId);

	SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;
}

