package com.liuhaibin.myblog.config;

import com.liuhaibin.myblog.anth.AuthenticationEntryPointImpl;
import com.liuhaibin.myblog.anth.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * 配置 这个  会替换 springsecurity 的验证流程不再显示login页面   对于未放行接口 显示 403
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 对于图片访问 允许匿名访问
                .antMatchers("/opt/deploy/upload/**").anonymous()
                // 对于博客展示页面放开
                .antMatchers("/blogInfo/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //添加自定义的 授权认证失败处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        //开启跨域
        http.cors();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
