package com.louisblogs.louismall.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/23 23:05
 */

@Data
public class MemberRegistVo {

	private String userName;

	private String password;

	private String phone;

}
