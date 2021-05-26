package com.louisblogs.louismall.product.exception;


import com.louisblogs.common.exception.BizCodeEnume;
import com.louisblogs.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：luqi
 * @description：集中处理所有异常
 * @date ：2021/5/26 18:54
 */

@Slf4j
//@ResponseBody
//@ControllerAdvice(basePackages = "com.louisblogs.louismall.product.controller")
@RestControllerAdvice(basePackages = "com.louisblogs.louismall.product.controller")
public class LouismallExceptionControllerAdvice {

	@ResponseBody
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public R handleVaildException(MethodArgumentNotValidException e) {
		log.error("数据校验出现问题:{}，异常类型:{}", e.getMessage(), e.getClass());
		BindingResult bindingResult = e.getBindingResult();

		// 遍历字段错误
		Map<String, String> errorMap = new HashMap<>();
		bindingResult.getFieldErrors().forEach((fieldError) -> {
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
		});

		return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
	}

	@ExceptionHandler(value = Throwable.class)
	public R handleException(Throwable throwable) {

		return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg());
	}

}
