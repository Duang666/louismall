package com.louisblogs.louismall.member.exception;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 23:45
 */

public class UsernameExistException extends RuntimeException {

	public UsernameExistException(){
		super("用户名存在");
	}

}
