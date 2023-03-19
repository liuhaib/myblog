package com.liuhaibin.myblog.exception;

import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = BizException.class)
    public Result errorHandler(BizException e) {
        return Result.error601(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.error101();
    }




}


