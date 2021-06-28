package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.SpuInfoDescEntity;
import com.louisblogs.louismall.product.entity.SpuInfoEntity;
import com.louisblogs.louismall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveSpuInfo(SpuSaveVo vo);

	//1、保存spu基本信息
	void saveBaseSpuInfo(SpuInfoEntity infoEntity);

	//spu检索
	PageUtils queryPageByCondition(Map<String, Object> params);

	//商品上架
	void up(Long spuId);

	SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

