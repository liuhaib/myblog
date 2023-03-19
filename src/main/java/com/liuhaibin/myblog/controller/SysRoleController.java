package com.liuhaibin.myblog.controller;


import cn.hutool.core.collection.CollUtil;
import com.liuhaibin.myblog.dto.RoleDTO;
import com.liuhaibin.myblog.service.SysRoleService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  角色控制器
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
@RestController
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;

    @GetMapping("/role/all")
    public Result GetallRole(){
        return sysRoleService.getallRole();
    }


    @GetMapping("/role/roles")
    public Result listRoles(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String keywords) {
        return sysRoleService.listRoles(pageNum,pageSize,keywords);
    }

    @DeleteMapping("/role/delete")
    public Result deletebyids(@RequestBody List<Long> roleIdList){
        if(CollUtil.isEmpty(roleIdList)){
            return Result.error("数据为空！");
        }
        return sysRoleService.deleteRole(roleIdList);
    }

    @PostMapping("/role/saveorupdate")
    public Result SaveOrUpdate(@RequestBody RoleDTO roleDTO){
        return sysRoleService.SaveOrUpdate(roleDTO);
    }

}

