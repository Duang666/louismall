package com.louisblogs.louismall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/5 20:20
 */

@Data
public class MergeVo {

	private Long purchaseId; //整单id
	private List<Long> items; //合并项集合

}
