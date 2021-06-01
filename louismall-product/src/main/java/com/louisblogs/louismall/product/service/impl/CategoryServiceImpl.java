package com.louisblogs.louismall.product.service.impl;

import com.louisblogs.louismall.product.entity.CategoryBrandRelationEntity;
import com.louisblogs.louismall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.product.dao.CategoryDao;
import com.louisblogs.louismall.product.entity.CategoryEntity;
import com.louisblogs.louismall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//	@Autowired
//	CategoryDao categoryDao;

	@Autowired
	CategoryBrandRelationService categoryBrandRelationService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<CategoryEntity> page = this.page(
				new Query<CategoryEntity>().getPage(params),
				new QueryWrapper<CategoryEntity>()
		);

		return new PageUtils(page);
	}

	//查出所有分类以及子分类，以树形结构组装起来
	@Override
	public List<CategoryEntity> listWithTree() {
		//1、查出所有分类
		List<CategoryEntity> entities = baseMapper.selectList(null);

		//2、组装成父子的树形结构
		//2.1、找到所有的一级分类
		List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
				categoryEntity.getParentCid() == 0
		).map((menu) -> {
			menu.setChildren(getChildren(menu, entities));
			return menu;
		}).sorted((menu1, menu2) -> {
			//return menu1.getSort() - menu2.getSort();   报空指针异常
			return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
		}).collect(Collectors.toList());

		return level1Menus;
	}

	//递归查找所有菜单子菜单
	private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
		List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
			return categoryEntity.getParentCid().equals(root.getCatId());
		}).map(categoryEntity -> {
			//1、找到子菜单
			categoryEntity.setChildren(getChildren(categoryEntity, all));
			return categoryEntity;
		}).sorted((menu1, menu2) -> {
			//2、菜单的排序
			return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
		}).collect(Collectors.toList());

		return children;
	}

	//删除菜单
	@Override
	public void removeMenuByIds(List<Long> asList) {
		//TODO 1、检查当前删除的菜单，是否被别的地方引用

		//

		baseMapper.deleteBatchIds(asList);
	}

	//找到catelogId的完整路径：[父/子/孙]
	@Override
	public Long[] findCatelogPath(Long catelogId) {
		List<Long> paths = new ArrayList<>();
		List<Long> parentPath = findParentPath(catelogId, paths);

		//逆序转换
		Collections.reverse(parentPath);

		return parentPath.toArray(new Long[parentPath.size()]);
	}

	private List<Long> findParentPath(Long catelogId, List<Long> paths) {
		//1、收集当前节点id
		paths.add(catelogId);
		CategoryEntity byId = this.getById(catelogId);
		if (byId.getParentCid() != 0) {
			findParentPath(byId.getParentCid(), paths);
		}
		return paths;
	}

	//级联更新所有关联的数据
	@Transactional
	@Override
	public void updateCascade(CategoryEntity category) {
		this.updateById(category);
		categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
	}
}



