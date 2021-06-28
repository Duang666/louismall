package com.louisblogs.louismall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.member.entity.MemberReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 11:04:54
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<MemberReceiveAddressEntity> getAddress(Long memberId);
}

