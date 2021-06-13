package com.louisblogs.louismall.product.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：luqi
 * @description：2级分类vo
 * @date ：2021/6/13 14:20
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Catelog2Vo {

	private String catalog1Id;  //1级父分类id

	private List<Catalog3Vo> catalog3List;  //三级子分类id

	private String id;

	private String name;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Catalog3Vo{
		private String catalog2Id;  //父分类，2级分类id
		private String id;
		private String name;
	}

}
