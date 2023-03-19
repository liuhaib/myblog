package com.liuhaibin.myblog.anth;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhaibin.myblog.pojo.SysUser;
import com.liuhaibin.myblog.service.SysRoleService;
import com.liuhaibin.myblog.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 继承 UserDetailsService 重写用户  从数据库获取
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SysUserService sysUserService;

    @Resource
    SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",username);
        SysUser user = sysUserService.getOne(queryWrapper);
        if(ObjectUtil.isNull(user)){
            throw new RuntimeException("用户名密码错误");
        }
        if(user.getStatus() == 1){
            throw new RuntimeException("该账号已被禁用，请联系管理员");
        }
        // TODO  2022/5/3 权限 获取
        return new MyUserDetails(user);
    }
}
