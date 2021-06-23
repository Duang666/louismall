package com.louisblogs.louismall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/22 21:38
 */

@ToString
@Data
public class SpuItemAttrGroupVo {

	private String groupName;
	private List<Attr> attrs;

}
