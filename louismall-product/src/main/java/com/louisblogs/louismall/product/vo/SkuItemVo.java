package com.louisblogs.louismall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.louisblogs.louismall.product.entity.SkuImagesEntity;
import com.louisblogs.louismall.product.entity.SkuInfoEntity;
import com.louisblogs.louismall.product.entity.SpuInfoDescEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/22 19:45
 */

@Data
public class SkuItemVo {

	//1、sku基本信息获取 pms_sku_info
	SkuInfoEntity info;

	boolean hasStock = true;

	//2、sku的图片信息 pms_sku_images
	List<SkuImagesEntity> images;

	//3、获取spu的销售属性组合
	List<SkuItemSaleAttrVo> saleAttr;

	//4、获取spu的介绍
	SpuInfoDescEntity desp;

	//5、获取spu的规格参数信息
	List<SpuItemAttrGroupVo> groupAttrs;

	//6 当前商品秒杀的优惠信息
	private SeckillSkuInfoVo seckillSkuInfoVo;

}
