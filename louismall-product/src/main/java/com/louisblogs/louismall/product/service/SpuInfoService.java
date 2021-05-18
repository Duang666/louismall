package com.louisblogs.louismall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

