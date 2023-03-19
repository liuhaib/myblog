package com.liuhaibin.myblog.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.liuhaibin.myblog.dto.PasswordDto;
import com.liuhaibin.myblog.dto.SysUserDto;
import com.liuhaibin.myblog.pojo.SysUser;
import com.liuhaibin.myblog.service.SysUserService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户控制器
 * </p>
 *
 * @author haibin
 * @since 2022-04-27
 */
@RestController
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService){
        this.sysUserService = sysUserService;
    }

    /**
     * 用户登入
     * @param loginUser 登入用户对象
     * @return
     */
    @PostMapping("/user/login")
    public Result Login(@RequestBody SysUserDto loginUser){
        if(loginUser.getUsername()==null){
            return Result.error("用户数据为空,请輸入账号密码！");
        }
        return sysUserService.UserLogin(loginUser);
    }

    /**
     * 用户数据
     * @param pageNum  参数
     * @param pageSize 参数
     * @param keywords 参数
     * @return
     */
    @GetMapping("/user/users")
    public Result GetList(@RequestParam Integer pageNum,
                         @RequestParam Integer pageSize,
                         @RequestParam(defaultValue = "") String keywords){
        return sysUserService.getAll(pageNum,pageSize,keywords);
    }

    /**
     * 修改用户账号状态
     * @param id 参数
     * @param status 参数
     * @return
     */
    @PutMapping("/user/disable/{id}")
    public Result disable(@PathVariable("id") Integer id, @RequestBody Integer status){
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("status",status);
        boolean update = sysUserService.update(wrapper);
        if(!update){
            return Result.error("更新失败");
        }
        return Result.success();
    }


    /**
     * 用户信息修改
     * @param user
     * @return
     */
    @PutMapping("/user/update")
    public Result UpdateByid(@RequestBody SysUser user){
        return sysUserService.updateUser(user);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public Result SaveUser(@RequestBody SysUser user){
        System.out.println(user);
        if(StrUtil.isBlank(user.getPassword())){
            return Result.error("请填写密码");
        }
        if(StrUtil.isBlank(user.getUsername())){
            return Result.error("请填写账号");
        }
        if(user.getRoleid() == null){
            return Result.error("请选择角色");
        }
        return sysUserService.AddUser(user);
    }


    /**
     * 退出
     * @return Result
     */
    @GetMapping("/user/logout")
    public Result LogOut(){
        return sysUserService.LoGOut();
    }


    /**
     * 用户删除
     * @param id
     * @return Result
     */
    @DeleteMapping("/user/deleteUser")
    public Result deleteUser(@RequestParam("id") Long id){
       return sysUserService.deleteUser(id);
    }

    /**
     * 修改密码
     * @param passwordDto
     * @return
     */
    @PutMapping("/user/password")
    public Result updatapassword(@RequestBody PasswordDto passwordDto){
       return   sysUserService.updatePassword(passwordDto);
    }

}

