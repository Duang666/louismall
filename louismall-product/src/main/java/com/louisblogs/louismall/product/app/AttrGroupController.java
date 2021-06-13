package com.louisblogs.louismall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.louisblogs.louismall.product.entity.AttrEntity;
import com.louisblogs.louismall.product.service.AttrAttrgroupRelationService;
import com.louisblogs.louismall.product.service.AttrService;
import com.louisblogs.louismall.product.service.CategoryService;
import com.louisblogs.louismall.product.vo.AttrGroupRelationVo;
import com.louisblogs.louismall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.louisblogs.louismall.product.entity.AttrGroupEntity;
import com.louisblogs.louismall.product.service.AttrGroupService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.R;


/**
 * 属性分组
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 10:44:59
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {

	@Autowired
	private AttrGroupService attrGroupService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	AttrService attrService;

	@Autowired
	AttrAttrgroupRelationService relationService;

	///product/attrgroup/attr/relation
	/**
	 * 添加属性与分组关联关系
	 */
	@PostMapping("/attr/relation")
	public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){
		relationService.saveBatch(vos);

		return R.ok();
	}

	///product/attrgroup/{catelogId}/withattr
	/**
	 * 获取分类下所有分组&关联属性
	 */
	@GetMapping("/{catelogId}/withattr")
	public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId){

		//1、查出当前分类下的所有属性分组
		//2、查出每个属性分组的所有属性
		List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
		return R.ok().put("data",vos);

	}

	///product/attrgroup/{attrgroupId}/attr/relation
	/**
	 * 获取指定分组关联的所有属性
	 */
	@GetMapping("/{attrgroupId}/attr/relation")
	public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
		List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
		return R.ok().put("data", entities);
	}

	///product/attrgroup/{attrgroupId}/noattr/relation
	/**
	 * 获取属性分组没有关联的其他属性
	 */
	@GetMapping("/{attrgroupId}/noattr/relation")
	public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
	                        @RequestParam Map<String, Object> params) {
		PageUtils page = attrService.getNoRelationAttr(params, attrgroupId);
		return R.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list/{catelogId}")
	//@RequiresPermissions("product:attrgroup:list")
	public R list(@RequestParam Map<String, Object> params,
	              @PathVariable("catelogId") Long catelogId) {
//        PageUtils page = attrGroupService.queryPage(params);

		PageUtils page = attrGroupService.queryPage(params, catelogId);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{attrGroupId}")
	//@RequiresPermissions("product:attrgroup:info")
	public R info(@PathVariable("attrGroupId") Long attrGroupId) {
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

		Long catelogId = attrGroup.getCatelogId();
		Long[] path = categoryService.findCatelogPath(catelogId);

		attrGroup.setCatelogPath(path);

		return R.ok().put("attrGroup", attrGroup);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("product:attrgroup:save")
	public R save(@RequestBody AttrGroupEntity attrGroup) {
		attrGroupService.save(attrGroup);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("product:attrgroup:update")
	public R update(@RequestBody AttrGroupEntity attrGroup) {
		attrGroupService.updateById(attrGroup);

		return R.ok();
	}


	/**
	 * 删除属性与分组的关联关系
	 *
	 * @param vos
	 * @return
	 */
	///product/attrgroup/attr/relation/delete
	@PostMapping("/attr/relation/delete")
	public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
		attrService.deleteRelation(vos);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("product:attrgroup:delete")
	public R delete(@RequestBody Long[] attrGroupIds) {
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

		return R.ok();
	}

}
