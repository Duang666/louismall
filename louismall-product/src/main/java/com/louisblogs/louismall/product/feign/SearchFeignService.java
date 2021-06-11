package com.louisblogs.louismall.product.feign;

import com.louisblogs.common.to.es.SkuEsModel;
import com.louisblogs.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/10 16:02
 */

@FeignClient("louismall-search")
public interface SearchFeignService {

	@PostMapping("/search/save/product")
	R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
