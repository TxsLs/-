package org.boot.hf.admin.controller;

import javax.validation.ValidationException;

import org.quincy.rock.core.vo.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <b>ControllerExceptionAdvice。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author wks
 * @since 1.0
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public Result<?> defaultExceptionHandler(Exception ex) {
		return Result.toResult("2000", "捕获异常!", ex).result(Boolean.FALSE);
	}

	@ExceptionHandler({ BindException.class, ValidationException.class, MissingServletRequestParameterException.class })
	public Result<?> validExceptionHandler(Exception ex) {
		return Result.toResult("1999", "校验异常!", ex).result(Boolean.FALSE);
	}

}
