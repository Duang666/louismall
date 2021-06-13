package com.louisblogs.louismall.product.service.impl;

import com.louisblogs.louismall.product.entity.CategoryBrandRelationEntity;
import com.louisblogs.louismall.product.service.CategoryBrandRelationService;
import com.louisblogs.louismall.product.vo.front.Catelog2Vo;
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
		categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
	}

	//渲染首页数据
	@Override
	public List<CategoryEntity> getLevel1Categorys() {
		List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

		return categoryEntities;
	}

	@Override
	public Map<String, List<Catelog2Vo>> getCatalogJson() {
		//1、查出所有1级分类
		List<CategoryEntity> level1Categorys = getLevel1Categorys();

		//2、封装数据
		Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
			//1、每一个的一级分类，查到一级分类的二级分类
			List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
			//2、封装上面的结果
			List<Catelog2Vo> catelog2Vos = null;
			if (categoryEntities != null) {
				catelog2Vos = categoryEntities.stream().map(l2 -> {
					Catelog2Vo catelog2Vo = new Catelog2Vo("v.getCatId().toString()", null, "item.getCatId().toString()", l2.getName());

					//1、找当前二级分类的三级分类
					List<CategoryEntity> level3Catelog = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", l2.getCatId()));
					if (level3Catelog != null) {
						List<Catelog2Vo.Catalog3Vo> collect = level3Catelog.stream().map(l3 -> {
							//2、封装成指定格式
							Catelog2Vo.Catalog3Vo catalog3Vo = new Catelog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
							return catalog3Vo;
						}).collect(Collectors.toList());
						catelog2Vo.setCatalog3List(collect);
					}

					return catelog2Vo;
				}).collect(Collectors.toList());
			}

			return catelog2Vos;
		}));

		return parent_cid;
	}
}



