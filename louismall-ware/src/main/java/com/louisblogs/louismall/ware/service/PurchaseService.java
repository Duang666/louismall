package com.louisblogs.louismall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.ware.entity.PurchaseEntity;
import com.louisblogs.louismall.ware.vo.MergeVo;
import com.louisblogs.louismall.ware.vo.PurshaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:29:32
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

	//查询未领取的采购单
	PageUtils queryPageUnreceive(Map<String, Object> params);

	//合并采购需求
	void mergePurchase(MergeVo mergeVo);

	//领取采购单
	void received(List<Long> ids);

	//完成采购
	void done(PurshaseDoneVo doneVo);
}

