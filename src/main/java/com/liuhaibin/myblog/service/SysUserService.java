package com.liuhaibin.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhaibin.myblog.dto.PasswordDto;
import com.liuhaibin.myblog.dto.SysUserDto;
import com.liuhaibin.myblog.pojo.SysUser;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author haibin
 * @since 2022-04-27
 */
public interface SysUserService extends IService<SysUser> {

    Result UserLogin(SysUserDto loginUser);

    Result getAll(Integer pageNum, Integer pageSize, String keywords);

    @Transactional
    Result updateUser(SysUser user);

    @Transactional
    Result AddUser(SysUser user);

    Result LoGOut();

    @Transactional
    Result deleteUser(Long id);

    Result updatePassword(PasswordDto passwordDto);
}
