package com.louisblogs.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/5/26 20:40
 */

public class ListValueConstraintVAlidator implements ConstraintValidator<ListValue, Integer> {

	private Set<Integer> set = new HashSet<>();
	// 初始化方法
	@Override
	public void initialize(ListValue constraintAnnotation) {

		int[] vals = constraintAnnotation.vals();
		for (int val : vals) {
			set.add(val);
		}

	}

	// 判断是否校验成功
	/**
	 * @param value 需要校验的值
	 * @param context
	 * @return
	 */
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return set.contains(value);
	}
}
