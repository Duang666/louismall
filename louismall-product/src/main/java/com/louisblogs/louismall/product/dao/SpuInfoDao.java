package com.louisblogs.louismall.product.dao;

import com.louisblogs.louismall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

	void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);

}
