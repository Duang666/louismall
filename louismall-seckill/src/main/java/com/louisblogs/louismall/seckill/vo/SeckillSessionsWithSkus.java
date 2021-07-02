package com.louisblogs.louismall.seckill.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/7/1 20:40
 */

@Data
public class SeckillSessionsWithSkus {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 场次名称
	 */
	private String name;
	/**
	 * 每日开始时间
	 */
	private Date startTime;
	/**
	 * 每日结束时间
	 */
	private Date endTime;
	/**
	 * 启用状态
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	private List<SeckillSkuVo> seckillSkuRelationEntities;
}
