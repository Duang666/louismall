package com.louisblogs.louismall.product.dao;

import com.louisblogs.louismall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.louisblogs.louismall.product.vo.AttrGroupRelationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

	void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);

}
