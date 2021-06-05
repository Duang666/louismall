package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.BrandEntity;
import com.louisblogs.louismall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //保存品牌分类关联
	void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

	//同步更新其他关联表中的数据
	void updateBrand(Long brandId, String name);

	//同步三级分类级联数据
	void updateCategory(Long catId, String name);

	//获取分类关联的品牌
	List<BrandEntity> getBrandsByCatId(Long catId);
}

