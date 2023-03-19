package com.liuhaibin.myblog.mapper;

import com.liuhaibin.myblog.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author haibin
 * @since 2022-04-27
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

}
