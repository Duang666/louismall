package com.louisblogs.louismall.coupon.service.impl;

import com.louisblogs.louismall.coupon.entity.SeckillSkuRelationEntity;
import com.louisblogs.louismall.coupon.service.SeckillSkuRelationService;
import com.louisblogs.louismall.coupon.utils.CouponTimeForStringUtils;
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

import com.louisblogs.louismall.coupon.dao.SeckillSessionDao;
import com.louisblogs.louismall.coupon.entity.SeckillSessionEntity;
import com.louisblogs.louismall.coupon.service.SeckillSessionService;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

	@Autowired
	SeckillSkuRelationService seckillSkuRelationService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SeckillSessionEntity> page = this.page(
				new Query<SeckillSessionEntity>().getPage(params),
				new QueryWrapper<SeckillSessionEntity>()
		);

		return new PageUtils(page);
	}

	/**
	 * 给远程服务gulimall-seckill调用
	 * 扫描需要参与秒杀的活动
	 */
	@Override
	public List<SeckillSessionEntity> getLatest3DaysSession() {

		//计算最近3天
		String start = CouponTimeForStringUtils.startTimeString();
		String end = CouponTimeForStringUtils.endTimeForString();
		List<SeckillSessionEntity> seckillSessionEntities = this.list(new QueryWrapper<SeckillSessionEntity>().between("start_time", start, end));
		if (seckillSessionEntities != null && seckillSessionEntities.size() > 0) {
			List<SeckillSessionEntity> seckillSessionEntities1 = seckillSessionEntities.stream().map((seckillSessionEntity) -> {
				Long id = seckillSessionEntity.getId();
				List<SeckillSkuRelationEntity> seckillSkuRelationEntities = seckillSkuRelationService.list(new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id", id));
				seckillSessionEntity.setSeckillSkuRelationEntities(seckillSkuRelationEntities);
				return seckillSessionEntity;
			}).collect(Collectors.toList());
			return seckillSessionEntities1;
		}
		return null;
	}

}