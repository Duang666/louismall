package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.AttrEntity;
import com.louisblogs.louismall.product.vo.AttrGroupRelationVo;
import com.louisblogs.louismall.product.vo.AttrRespVo;
import com.louisblogs.louismall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void saveAttr(AttrVo attr);

	PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

	AttrRespVo getAttrInfo(Long attrId);

	void updateAttr(AttrRespVo attr);

	//获取指定分组关联的所有属性
	List<AttrEntity> getRelationAttr(Long attrgroupId);

	//删除属性与分组的关联关系
	void deleteRelation(AttrGroupRelationVo[] vos);

	//获取属性分组没有关联的其他属性
	PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

