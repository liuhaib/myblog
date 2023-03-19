package com.liuhaibin.myblog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  用户DTO
 */
@Data
@ApiModel(value="SysUser返回对象", description="用户信息表")
public class SysUserDto {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户账户")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户头像地址")
    private String headportrait;

    @ApiModelProperty(value = "个人简介")
    private String about;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "角色id")
    private Integer  roleid;
}
