package com.louisblogs.louismall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.louisblogs.louismall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/3 15:53
 */

@Data
public class AttrGroupWithAttrsVo {

	/**
	 * 分组id
	 */
	private Long attrGroupId;
	/**
	 * 组名
	 */
	private String attrGroupName;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 描述
	 */
	private String descript;
	/**
	 * 组图标
	 */
	private String icon;
	/**
	 * 所属分类id
	 */
	private Long catelogId;
	/**
	 * 所有属性
	 */
	private List<AttrEntity> attrs;

}
