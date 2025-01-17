package com.louisblogs.louismall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.product.entity.SkuImagesEntity;
import com.louisblogs.louismall.product.entity.SpuInfoDescEntity;
import com.louisblogs.louismall.product.feign.SeckillFeignService;
import com.louisblogs.louismall.product.service.*;
import com.louisblogs.louismall.product.vo.SeckillSkuInfoVo;
import com.louisblogs.louismall.product.vo.SkuItemSaleAttrVo;
import com.louisblogs.louismall.product.vo.SkuItemVo;
import com.louisblogs.louismall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.product.dao.SkuInfoDao;
import com.louisblogs.louismall.product.entity.SkuInfoEntity;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

	@Autowired
	SkuImagesService imagesService;

	@Autowired
	SpuInfoDescService spuInfoDescService;

	@Autowired
	AttrGroupService attrGroupService;

	@Autowired
	SkuSaleAttrValueService skuSaleAttrValueService;

	@Autowired
	ThreadPoolExecutor executor;

	@Autowired
	SeckillFeignService seckillFeignService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SkuInfoEntity> page = this.page(
				new Query<SkuInfoEntity>().getPage(params),
				new QueryWrapper<SkuInfoEntity>()
		);

		return new PageUtils(page);
	}

	@Override
	public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
		this.baseMapper.insert(skuInfoEntity);
	}

	/**
	 * sku检索
	 *
	 * @param params
	 * @return
	 */
	@Override
	public PageUtils queryPageByCondition(Map<String, Object> params) {
		QueryWrapper<SkuInfoEntity> querywrapper = new QueryWrapper<>();
		/**
		 * key:
		 * catelogId: 0
		 * brandId: 0
		 * min: 0
		 * max: 0
		 */
		String key = (String) params.get("key");
		if (!StringUtils.isEmpty(key)) {
			querywrapper.and((wrapper) -> {
				wrapper.eq("sku_id", key).or().like("sku_name", key);
			});
		}
		String catelogId = (String) params.get("catelogId");
		if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
			querywrapper.eq("catelog_id", catelogId);
		}
		String brandId = (String) params.get("brandId");
		if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
			querywrapper.eq("brand_id", brandId);
		}
		String min = (String) params.get("min");
		if (!StringUtils.isEmpty(min)) {
			querywrapper.ge("price", min);
		}
		String max = (String) params.get("max");
		if (!StringUtils.isEmpty(max)) {
			try {
				BigDecimal bigDecimal = new BigDecimal(max);

				if (bigDecimal.compareTo(new BigDecimal("0")) == 1) {
					querywrapper.le("price", max);
				}
			} catch (Exception e) {

			}
		}


		IPage<SkuInfoEntity> page = this.page(
				new Query<SkuInfoEntity>().getPage(params),
				querywrapper
		);

		return new PageUtils(page);
	}

	@Override
	public List<SkuInfoEntity> getSkuBySpuId(Long spuId) {
		List<SkuInfoEntity> list = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id",spuId));
		return list;
	}

	@Override
	public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
		SkuItemVo skuItemVo = new SkuItemVo();

		CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
			//1、sku基本信息获取 pms_sku_info
			SkuInfoEntity info = getById(skuId);
			skuItemVo.setInfo(info);
			return info;
		}, executor);

		CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
			//3、获取spu的销售属性组合
			List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
			skuItemVo.setSaleAttr(saleAttrVos);
		}, executor);

		CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
			//4、获取spu的介绍 pms_spu_info_desc
			SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
			skuItemVo.setDesp(spuInfoDescEntity);
		}, executor);

		CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
			//5、获取spu的规格参数信息
			List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatalogId());
			skuItemVo.setGroupAttrs(attrGroupVos);
		}, executor);

		CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
			//2、sku的图片信息 pms_sku_images
			List<SkuImagesEntity> images = imagesService.getImagesBySkuId(skuId);
			skuItemVo.setImages(images);
		}, executor);

		CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
			//3、查询当前sku是否参加秒杀优惠
			R r = seckillFeignService.getSkuSeckillInfo(skuId);
			if (r.getCode() == 0) {
				SeckillSkuInfoVo seckillSkuRedisVo = r.getData(new TypeReference<SeckillSkuInfoVo>() {
				});
				skuItemVo.setSeckillSkuInfoVo(seckillSkuRedisVo);
			}
		}, executor);

		//等待所有任务都完成
		CompletableFuture.allOf(saleAttrFuture,descFuture,baseAttrFuture,imageFuture,seckillFuture).get();

		return skuItemVo;
	}

}