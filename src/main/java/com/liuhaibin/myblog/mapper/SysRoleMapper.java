package com.liuhaibin.myblog.mapper;

import com.liuhaibin.myblog.dto.RoleDTO;
import com.liuhaibin.myblog.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据id 获取角色
     * @param id
     * @return
     */
    List<String> GetRoleById( Long id);


    /**
     * 查询角色列表
     *
     * @param current     页码
     * @param size        条数
     * @param keywords    条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(@Param("current") Integer current, @Param("size") Integer size, @Param("keywords") String keywords);

}
