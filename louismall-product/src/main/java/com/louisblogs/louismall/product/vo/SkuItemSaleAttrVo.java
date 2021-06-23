package com.louisblogs.louismall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/22 21:42
 */

@ToString
@Data
public class SkuItemSaleAttrVo {

	private Long attrId;
	private String attrName;
	private List<AttrValueWithSkuIdVo> attrValues;

}
