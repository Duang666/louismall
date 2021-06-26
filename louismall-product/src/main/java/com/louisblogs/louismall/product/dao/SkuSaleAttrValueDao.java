package com.louisblogs.louismall.product.dao;

import com.louisblogs.louismall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.louisblogs.louismall.product.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

	List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);

	List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);

}
