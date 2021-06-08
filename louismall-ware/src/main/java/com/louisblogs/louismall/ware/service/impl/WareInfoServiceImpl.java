package com.louisblogs.louismall.ware.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.ware.dao.WareInfoDao;
import com.louisblogs.louismall.ware.entity.WareInfoEntity;
import com.louisblogs.louismall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String key = (String) params.get("key");

		QueryWrapper<WareInfoEntity> wareInfoEntityQueryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(key)){
			wareInfoEntityQueryWrapper.eq("id",key)
					.or().like("name",key)
					.or().like("address",key)
					.or().like("areacode",key);
		}

		IPage<WareInfoEntity> page = this.page(
				new Query<WareInfoEntity>().getPage(params),
				wareInfoEntityQueryWrapper
		);

		return new PageUtils(page);
	}

}