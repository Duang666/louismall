package com.louisblogs.louismall.ware.dao;

import com.louisblogs.louismall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:29:31
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

	void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

	Long getSkuStock(Long skuId);

	List<Long> listWareIdHasSkuStock(@Param("skuId") Long skuId);

	Long lockSkuStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);
}
