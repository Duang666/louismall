package com.louisblogs.louismall.product.feign.fallback;

import com.louisblogs.common.exception.BizCodeEnume;
import com.louisblogs.common.utils.R;
import com.louisblogs.louismall.product.feign.SeckillFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/2 21:45
 */

@Slf4j
@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {

	@Override
	public R getSkuSeckillInfo(Long skuId) {
		log.info("熔断方法调用 getSkuSeckillInfo(). ..");
		return R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(), BizCodeEnume.TO_MANY_REQUEST.getMsg());
	}

}
