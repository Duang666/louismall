package com.louisblogs.louismall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.coupon.dao.SeckillSkuRelationDao;
import com.louisblogs.louismall.coupon.entity.SeckillSkuRelationEntity;
import com.louisblogs.louismall.coupon.service.SeckillSkuRelationService;
import org.springframework.util.StringUtils;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {

		QueryWrapper<SeckillSkuRelationEntity> queryWrapper = new QueryWrapper<SeckillSkuRelationEntity>();
		String promotionSessionId = (String) params.get("promotionSessionId");
		if (!StringUtils.isEmpty(promotionSessionId)) {
			//场次id非空
			queryWrapper.eq("promotion_session_id", promotionSessionId);
		}
		IPage<SeckillSkuRelationEntity> page = this.page(
				new Query<SeckillSkuRelationEntity>().getPage(params),
				queryWrapper
		);
		return new PageUtils(page);
	}

}