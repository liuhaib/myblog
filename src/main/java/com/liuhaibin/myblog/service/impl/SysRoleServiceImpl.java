package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.dto.RoleDTO;
import com.liuhaibin.myblog.dto.UserRoleDTO;
import com.liuhaibin.myblog.exception.BizException;
import com.liuhaibin.myblog.mapper.SysRoleMapper;
import com.liuhaibin.myblog.mapper.SysRoleMenuMapper;
import com.liuhaibin.myblog.mapper.SysRoleResourceMapper;
import com.liuhaibin.myblog.mapper.SysUserRoleMapper;
import com.liuhaibin.myblog.pojo.*;
import com.liuhaibin.myblog.service.SysRoleService;
import com.liuhaibin.myblog.uaits.BeanCopyUtil;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public List<String> GetRoleByUserId(Long id) {//根据id 获取 角色
        return this.baseMapper.GetRoleById(id);
    }

    @Override
    public Result getallRole() {
        List<SysRole> list = list();
        List<UserRoleDTO> userRoleDTOS = BeanCopyUtil.copyList(list, UserRoleDTO.class);
        return Result.success(userRoleDTOS);
    }

    @Override
    public Result listRoles(Integer pageNum, Integer pageSize, String keywords) {

        List<RoleDTO> roleDTOList = this.baseMapper.listRoles(pageNum-1, pageSize, keywords);
        // 查询总量
        Integer count = this.baseMapper.selectCount(new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(keywords), SysRole::getRoleName, keywords));

        Map<String,Object> map = new HashMap<>();
        map.put("count",count);
        map.put("recordList",roleDTOList);

        return Result.success(map);
    }

    @Override
    public Result deleteRole(List<Long> roleIdList) {
        // 判断角色下是否有用户
        Integer count = sysUserRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>()
                .in(SysUserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new BizException("该角色下存在用户");
        }
        // 没有删除 角色
        removeByIds(roleIdList);
        // 删除 角色 菜单
        sysRoleMenuMapper.DeleteRoleMenuByID(roleIdList);
        // 删除 角色 资源
        sysRoleResourceMapper.DeleteRoleResourceByID(roleIdList);

        return Result.success();
    }

    @Override
    public Result SaveOrUpdate(RoleDTO roleDTO) {
        //判断角色是否已经存在
       if(roleDTO.getId()==null) {
           SysRole one = getOne(new QueryWrapper<SysRole>().eq("role_name", roleDTO.getRoleName()));
           if (ObjectUtil.isNotNull(one)) {
               return Result.error("角色名称已存在！");
           }
       }
        //更新 或者 添加 角色信息
        SysRole sysRole = BeanUtil.copyProperties(roleDTO, SysRole.class);
        if (sysRole.getCreateTime() == null) {
            sysRole.setCreateTime(new Date());
        } else {
            sysRole.setUpdateTime(new Date());
        }
        saveOrUpdate(sysRole);
        //更新 角色菜单
        if(CollUtil.isNotEmpty(roleDTO.getMenuIdList())){
            if(roleDTO.getId()==null){
                //获取角色id
                SysRole two = getOne(new QueryWrapper<SysRole>().eq("role_name", roleDTO.getRoleName()));
                //添加新菜单
                sysRoleMenuMapper.InsterRoleMenuByID(roleDTO.getMenuIdList(),two.getId().intValue());
            }else {
                //删除 之前菜单
                sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",roleDTO.getId()));
                //添加新菜单
                sysRoleMenuMapper.InsterRoleMenuByID(roleDTO.getMenuIdList(),roleDTO.getId().intValue());
            }
        }
        //更新 角色资源
        if(CollUtil.isNotEmpty(roleDTO.getResourceIdList())){
            if(roleDTO.getId()==null){
                //获取角色id
                SysRole two = getOne(new QueryWrapper<SysRole>().eq("role_name", roleDTO.getRoleName()));
                //添加新菜单
                sysRoleResourceMapper.InsterRoleResourceByID(roleDTO.getResourceIdList(),two.getId().intValue());
            }else {
                //删除 之前菜单
                sysRoleResourceMapper.delete(new QueryWrapper<SysRoleResource>().eq("role_id",roleDTO.getId()));
                //添加新菜单
                sysRoleResourceMapper.InsterRoleResourceByID(roleDTO.getResourceIdList(),roleDTO.getId().intValue());
            }
        }
        return Result.success();
    }
}
