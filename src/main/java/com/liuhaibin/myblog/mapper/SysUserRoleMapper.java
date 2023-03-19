package com.liuhaibin.myblog.mapper;

import com.liuhaibin.myblog.pojo.SysUser;
import com.liuhaibin.myblog.pojo.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haibin
 * @since 2022-05-14
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    int updateUserRole(SysUser user);

    int saveUserRole(SysUser user);

    int DeleteUserRoleByID (Long id);
}
