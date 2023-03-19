package com.liuhaibin.myblog.uaits;

import com.liuhaibin.myblog.constant.CodeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    /**
     * 返回的状态码  200  403  500
     */
    private String code;
    /**
     * 返回程序执行 成功 还是 失败
     */
    private boolean status;
    /**
     * 返回 提示 信息
     */
    private String message;
    /**
     * 返回 数据
     */
    private Object data;

    /**
     * 返回方式
     */
    public static Result success(){
        return new Result(CodeConstant.CODE_200,true,null,null);
    }
    public static Result success(Object data){
        return new Result(CodeConstant.CODE_200,true,null,data);
    }

    public static Result error(String message){
        return new Result(CodeConstant.CODE_601,false,message,null);
    }
    public static Result error404(){
        return new Result(CodeConstant.CODE_404,false,"请求资源不存在",null);
    }
    public static Result error403(){
        return new Result(CodeConstant.CODE_403,false,"请求权限不足",null);
    }
    public static Result error601(String message) {
        return new Result(CodeConstant.CODE_601, false, message, null);
    }
    public static Result error101(){
        return new Result(CodeConstant.CODE_101,false,"系统繁忙",null);
    }

}
