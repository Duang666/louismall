package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.BrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //冗余存储更新修改
	void updateDetail(BrandEntity brand);

	List<BrandEntity> getBrandsByIds(List<Long> brandIds);
}

