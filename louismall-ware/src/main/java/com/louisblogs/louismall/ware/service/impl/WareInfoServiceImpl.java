package com.louisblogs.louismall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.ware.feign.MemberFeignService;
import com.louisblogs.louismall.ware.vo.FareVo;
import com.louisblogs.louismall.ware.vo.MemberAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

	@Autowired
	MemberFeignService memberFeignService;

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

	/**
	 * 根据用户的收货地址计算运费
	 */
	@Override
	public FareVo getFare(Long addrId) {

		//要返回的大对象
		FareVo fareVo = new FareVo();

		//远程查询用户地址信息
		R r = memberFeignService.addrInfo(addrId);
		MemberAddressVo data = r.getData("memberReceiveAddress", new TypeReference<MemberAddressVo>() {
		});

		if (data != null) {
			//FareVo第1个属性
			fareVo.setAddress(data);
			//简单处理 手机号末位当作运费
			String phone = data.getPhone();
			//123456789 9
			String substring = phone.substring(phone.length() - 1, phone.length());
			BigDecimal fare = new BigDecimal(substring);
			//FareVo第2个属性
			fareVo.setFare(fare);

			return fareVo;
		}
		return null;
	}

}