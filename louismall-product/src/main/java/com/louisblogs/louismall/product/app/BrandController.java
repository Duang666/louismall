package com.louisblogs.louismall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.louisblogs.common.valid.AddGroup;
import com.louisblogs.common.valid.UpdateGroup;
import com.louisblogs.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.louisblogs.louismall.product.entity.BrandEntity;
import com.louisblogs.louismall.product.service.BrandService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.R;


/**
 * 品牌
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
	@Autowired
	private BrandService brandService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("product:brand:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = brandService.queryPage(params);

		return R.ok().put("page", page);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{brandId}")
	//@RequiresPermissions("product:brand:info")
	public R info(@PathVariable("brandId") Long brandId) {
		BrandEntity brand = brandService.getById(brandId);

		return R.ok().put("brand", brand);
	}

	/**
	 * search调用的远程服务
	 */
	@GetMapping("/infos")
	public R BrandsInfo(@RequestParam("brandIds") List<Long> brandIds) {
		List<BrandEntity> brands = brandService.getBrandsByIds(brandIds);

		return R.ok().put("brand", brands);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("product:brand:save")
	public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand/*,BindingResult result*/) {
//		if (result.hasErrors()) {
//			Map<String, String> map = new HashMap<>();
//			//1、获取校验的错误结果
//			result.getFieldErrors().forEach((item) -> {
//				// FieldError 获取到错误提示
//				String message = item.getDefaultMessage();
//				// 获取错误属性的名字
//				String field = item.getField();
//				map.put(field, message);
//			});
//			return R.error(400, "提交的数据不合法").put("data", map);
//		} else {
//			brandService.save(brand);
//		}
		brandService.save(brand);

		return R.ok();
	}

	/**
	 * 冗余存储更新修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("product:brand:update")
	public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand) {
		brandService.updateDetail(brand);

		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/update/status")
	//@RequiresPermissions("product:brand:update")
	public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand) {
		brandService.updateById(brand);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("product:brand:delete")
	public R delete(@RequestBody Long[] brandIds) {
		brandService.removeByIds(Arrays.asList(brandIds));

		return R.ok();
	}

}
