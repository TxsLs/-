package org.study.spring.controller;

import org.quincy.rock.core.vo.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * <b>异常处理类</b>
 * <p><b>详细说明：使用@RestControllerAdvice注解来指示它是一个全局异常处理器</b></p>
 * <!-- 处理数据校验异常（BindException），并返回相应的错误结果。 -->
 * 无。
 * 
 * @version 1.0
 * @author Nymphet
 * @since 1.0
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({ BindException.class })
    public Result<?> MethodArgumentNotValidExceptionHandler(BindException ex) {
        // 当捕获到BindException异常时，会执行该方法进行异常处理
        // BindException是Spring框架中的一个异常类，用于处理数据校验失败的情况
        // 例如，在Controller方法中使用了@Valid注解对实体进行数据校验，如果校验失败就会抛出BindException异常

        return Result.toResult("1999", "校验异常!").result(Boolean.FALSE);
        // 返回一个封装了错误信息的Result对象
        // 这里的"1999"是错误码，"校验异常!"是错误消息
        // 使用Boolean.FALSE将结果设置为失败
    }
}
