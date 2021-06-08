package com.louisblogs.louismall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/8 10:36
 */

@Data
public class PurshaseDoneVo {

	@NotNull
	private Long id; //采购单id

	private List<PurchaseItemDoneVo> items;

}
