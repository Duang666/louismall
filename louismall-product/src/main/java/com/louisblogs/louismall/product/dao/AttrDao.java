package com.louisblogs.louismall.product.dao;

import com.louisblogs.louismall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

	List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);

}
