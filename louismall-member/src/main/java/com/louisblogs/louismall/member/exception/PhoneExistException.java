package com.louisblogs.louismall.member.exception;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 23:46
 */

public class PhoneExistException extends RuntimeException {

	public PhoneExistException(){
		super("手机号存在");
	}

}
