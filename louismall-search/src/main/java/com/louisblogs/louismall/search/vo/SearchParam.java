package com.louisblogs.louismall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：luqi
 * @description：封装页面所有可能传递过来的查询条件
 * @date ：2021/6/17 14:19
 */

@Data
public class SearchParam {

	private String keyword;    //页面传递过来的全文匹配关键字

	private Long catalog3Id;    //三级分类id

	/**
	 * sort=saleCount_asc/desc 倒序
	 * sort=skuPrice_asc/desc 根据价格
	 * sort=hotScore_asc/desc
	 */
	private String sort;    //排序条件

	/**
	 * hasStock(是否有货) skuPrice区间 brandId catalog3Id attrs
	 * hasStock 0/1
	 * skuPrice=1_500/500_/_500
	 * brandId = 1
	 * attrs1_5寸_6寸
	 * // 0 无库存 1有库存
	 */
	private Integer hasStock;   //是否只显示有货，0（无库存）1（有库存）

	private String skuPrice;    //价格区查询

	private List<Long> brandId;     //多个品牌id，可以多选

	private List<String> attrs;     //按照属性进行筛选

	private Integer pageNum = 1;    //页码

	private String _queryString;    //原生的所有查询条件

}
