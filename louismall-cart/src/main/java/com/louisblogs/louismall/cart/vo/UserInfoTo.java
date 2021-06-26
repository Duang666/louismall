package com.louisblogs.louismall.cart.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/25 21:33
 */

@ToString
@Data
public class UserInfoTo {

	private Long userId;
	private String userKey;

	private boolean tempUser = false;

}
