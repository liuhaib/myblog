package com.liuhaibin.myblog.anth;

import cn.hutool.json.JSONUtil;
import com.liuhaibin.myblog.constant.CodeConstant;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登入认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result(CodeConstant.CODE_401,false,"用户账号长期未使用，已自动下线",null);
        String json = JSONUtil.toJsonPrettyStr(result);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(json);
    }
}
