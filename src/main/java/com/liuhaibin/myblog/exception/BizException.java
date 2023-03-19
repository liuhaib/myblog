package com.liuhaibin.myblog.exception;


import com.liuhaibin.myblog.constant.CodeConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 业务异常
 */
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private String code = CodeConstant.CODE_601;

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }
}
