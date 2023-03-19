package com.liuhaibin.myblog.mapper;

import com.liuhaibin.myblog.pojo.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haibin
 * @since 2022-05-14
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    int DeleteRoleMenuByID (List<Long> roleIdList);

    int InsterRoleMenuByID (@Param("list") List<Integer> IdList,@Param("id") Integer roleId);

}
