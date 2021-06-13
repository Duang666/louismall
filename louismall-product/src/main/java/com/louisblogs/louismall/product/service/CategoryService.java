package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.CategoryEntity;
import com.louisblogs.louismall.product.vo.front.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //查出所有分类以及子分类，以树形结构组装起来
	List<CategoryEntity> listWithTree();

	//删除菜单
	void removeMenuByIds(List<Long> asList);

	//找到catelogId的完整路径：[父/子/孙]
	Long[] findCatelogPath(Long catelogId);

	//三级分类级联更新修改
	void updateCascade(CategoryEntity category);

	//渲染首页数据
	List<CategoryEntity> getLevel1Categorys();

	Map<String, List<Catelog2Vo>> getCatalogJson();
}

