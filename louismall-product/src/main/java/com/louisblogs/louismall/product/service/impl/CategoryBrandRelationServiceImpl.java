package com.louisblogs.louismall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.louisblogs.louismall.product.dao.BrandDao;
import com.louisblogs.louismall.product.dao.CategoryDao;
import com.louisblogs.louismall.product.entity.BrandEntity;
import com.louisblogs.louismall.product.entity.CategoryEntity;
import com.louisblogs.louismall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.product.dao.CategoryBrandRelationDao;
import com.louisblogs.louismall.product.entity.CategoryBrandRelationEntity;
import com.louisblogs.louismall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

	@Autowired(required = false)
	BrandDao brandDao;

	@Autowired(required = false)
	CategoryDao categoryDao;

	@Autowired(required = false)
	CategoryBrandRelationDao relationDao;

	@Autowired
	BrandService brandService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<CategoryBrandRelationEntity> page = this.page(
				new Query<CategoryBrandRelationEntity>().getPage(params),
				new QueryWrapper<CategoryBrandRelationEntity>()
		);

		return new PageUtils(page);
	}

	//保存品牌分类关联
	@Override
	public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
		Long brandId = categoryBrandRelation.getBrandId();
		Long catelogId = categoryBrandRelation.getCatelogId();
		//查询详细名字
		BrandEntity brandEntity = brandDao.selectById(brandId);
		CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

		categoryBrandRelation.setBrandName(brandEntity.getName());
		categoryBrandRelation.setCatelogName(categoryEntity.getName());

		this.save(categoryBrandRelation);
	}

	//同步更新其他关联表中的数据
	@Override
	public void updateBrand(Long brandId, String name) {
		CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
		relationEntity.setBrandId(brandId);
		relationEntity.setBrandName(name);
		this.update(relationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
	}

	//同步三级分类级联数据
	@Override
	public void updateCategory(Long catId, String name) {
		this.baseMapper.updateCategory(catId, name);
	}

	//获取分类关联的品牌
	@Override
	public List<BrandEntity> getBrandsByCatId(Long catId) {

		List<CategoryBrandRelationEntity> catelogId = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
		List<BrandEntity> collect = catelogId.stream().map(item -> {
			Long brandId = item.getBrandId();
			BrandEntity byId = brandService.getById(brandId);
			return byId;
		}).collect(Collectors.toList());
		return collect;
	}

}