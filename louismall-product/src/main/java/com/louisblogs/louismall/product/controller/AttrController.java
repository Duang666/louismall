package com.louisblogs.louismall.product.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.louisblogs.louismall.product.vo.AttrGroupRelationVo;
import com.louisblogs.louismall.product.vo.AttrRespVo;
import com.louisblogs.louismall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.louisblogs.louismall.product.entity.AttrEntity;
import com.louisblogs.louismall.product.service.AttrService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.R;


/**
 * 商品属性
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:58
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
	@Autowired
	private AttrService attrService;

	///product/attr/info/{attrId}

	///product/attr/sale/list/0?
	///product/attr/base/list/{catelogId}
	@GetMapping("/{attrType}/list/{catelogId}")
	public R baseAttrList(@RequestParam Map<String, Object> params,
	                      @PathVariable("catelogId") Long catelogId,
	                      @PathVariable("attrType") String type) {
		PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
		return R.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("product:attr:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = attrService.queryPage(params);

		return R.ok().put("page", page);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{attrId}")
	//@RequiresPermissions("product:attr:info")
	public R info(@PathVariable("attrId") Long attrId) {
//		AttrEntity attr = attrService.getById(attrId);
		AttrRespVo respVo = attrService.getAttrInfo(attrId);

		return R.ok().put("attr", respVo);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("product:attr:save")
	public R save(@RequestBody AttrVo attr) {
		attrService.saveAttr(attr);

		return R.ok();
	}

	/**
	 * 修改分组
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("product:attr:update")
	public R update(@RequestBody AttrRespVo attr) {
		attrService.updateAttr(attr);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("product:attr:delete")
	public R delete(@RequestBody Long[] attrIds) {
		attrService.removeByIds(Arrays.asList(attrIds));

		return R.ok();
	}

}
