package com.louisblogs.louismall.product.service.impl;

import com.louisblogs.louismall.product.entity.AttrEntity;
import com.louisblogs.louismall.product.service.AttrService;
import com.louisblogs.louismall.product.vo.AttrGroupWithAttrsVo;
import com.louisblogs.louismall.product.vo.SkuItemVo;
import com.louisblogs.louismall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.product.dao.AttrGroupDao;
import com.louisblogs.louismall.product.entity.AttrGroupEntity;
import com.louisblogs.louismall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

	@Autowired
	AttrService attrService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<AttrGroupEntity> page = this.page(
				new Query<AttrGroupEntity>().getPage(params),
				new QueryWrapper<AttrGroupEntity>()
		);

		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
		String key = (String) params.get("key");
		// select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
		QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();

		if (!StringUtils.isEmpty(key)) {
			wrapper.and((obj) -> {
				obj.eq("attr_group_id", key).or().like("attr_group_name", key);
			});
		}

		if (catelogId == 0) {
			IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
			return new PageUtils(page);
		} else {
			wrapper.eq("catelog_id", catelogId);
			IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);

			return new PageUtils(page);
		}
	}

	//获取分类id下所有分组&关联属性
	@Override
	public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {

		//1、查询分组信息
		List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

		//2、查询所有属性
		List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
			AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
			BeanUtils.copyProperties(group,attrsVo);
			List<AttrEntity> attrs = attrService.getRelationAttr(attrsVo.getAttrGroupId());
			attrsVo.setAttrs(attrs);
			return attrsVo;
		}).collect(Collectors.toList());

		return collect;

	}

	@Override
	public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
		//1、查出当前spu对应的所有属性的分组信息以及当前分组下的所有对应的值

		AttrGroupDao baseMapper = this.getBaseMapper();
		List<SpuItemAttrGroupVo> vos = baseMapper.getAttrGroupWithAttrsBySpuId(spuId,catalogId);
		return vos;
	}

}