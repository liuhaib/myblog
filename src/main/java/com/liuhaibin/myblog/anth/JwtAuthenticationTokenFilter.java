package com.liuhaibin.myblog.anth;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.liuhaibin.myblog.constant.RedisConstant;
import com.liuhaibin.myblog.uaits.JwtUait;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义 的 token过滤器  判断token
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //判断token是否存在
        if(StrUtil.isBlank(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        Long id = JwtUait.ParseTokenGetId(token);

        //从数据库或者缓存中获取用户信息
        String user = stringRedisTemplate.opsForValue().get(RedisConstant.RED_LOGIN + id);

        if(StrUtil.isBlank(user)){
            throw new RuntimeException("用户未登录");
        }

        MyUserDetails loginUser = JSONUtil.toBean(user, MyUserDetails.class);

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request, response);
    }
}
