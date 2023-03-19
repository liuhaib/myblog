package com.liuhaibin.myblog.controller;


import com.liuhaibin.myblog.dto.MenuDTO;
import com.liuhaibin.myblog.service.SysMenuService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  菜单控制器
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
@RestController
public class SysMenuController {

    @Resource
    SysMenuService sysMenuService;


    /**
     * 通过用户id  获取当前用户的菜单
     * @param id  参数
     * @return  返回当前用户菜单
     */
    @PostMapping("/menu/userMenu")
    public Result getUserMenu(@RequestBody Long id){
        return sysMenuService.GetMenuById(id);
    }

    /**
     * 查看 全部 菜单
     * @param keywords 参数
     * @return  全部菜单
     */
    @GetMapping("/menu/allMenu")
    public Result listMenus(@RequestParam(defaultValue = "") String keywords) {
        return Result.success(sysMenuService.listMenus(keywords));
    }

    /**
     * 查看角色菜单选项
     *
     * @return LabelOptionDTO 查看角色菜单选项
     */
    @GetMapping("/menu/menus")
    public Result listMenuOptions() {
        return Result.success(sysMenuService.listMenuOptions());
    }

    /**
     * 更新 或者 修改
     * @param menuDTO 参数
     * @return  返回值成功失败
     */
    @PostMapping("/menu/saveorupdate")
    public Result SaveOrUpdate(@RequestBody MenuDTO menuDTO){
       return sysMenuService.AddorUpdate(menuDTO);
    }

    /**
     * 删除 菜单
     * @param id 参数
     * @return  返回值成功失败
     */
    @DeleteMapping("/menu/deleteLink")
    public Result deleteMenu(@RequestParam("id") Long id){
        boolean b = sysMenuService.removeById(id);
        if (b) {
            return Result.success();
        }
       return Result.error("删除失败");
    }
}