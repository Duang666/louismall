package com.louisblogs.louismall.order.feign;

import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 21:01
 */

@FeignClient("louismall-ware")//这个远程服务
public interface WareFeignService {

	//远程查询商品的库存信息 查询sku是否有库存
	@PostMapping("/ware/waresku/hasStock")
	R getSkuHasStock(@RequestBody List<Long> skuIds);

	/**
	 * 根据用户的收货地址计算运费
	 */
	@GetMapping("/ware/wareinfo/fare")
	R getFare(@RequestParam("addrId") Long addrId);

	/**
	 * 为某个订单锁定库存
	 */
	@PostMapping("/ware/waresku/lock/order")
	R orderLockStock(@RequestBody WareSkuLockVo vo);

}