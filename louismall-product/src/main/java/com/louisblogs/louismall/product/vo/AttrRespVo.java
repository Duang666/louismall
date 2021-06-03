package com.louisblogs.louismall.product.vo;

import lombok.Data;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/1 21:46
 */

@Data
public class AttrRespVo extends AttrVo {

	/**
	 * "catelogName": "手机/数码/手机", //所属分类名字
	 * 	"groupName": "主体", //所属分组名字
	 */

	private String catelogName;

	private String groupName;

	private Long[] catelogPath;

}
