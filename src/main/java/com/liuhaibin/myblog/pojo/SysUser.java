package com.liuhaibin.myblog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author haibin
 * @since 2022-04-27
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户信息表")
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户账户")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户头像地址")
    private String headportrait;

    @ApiModelProperty(value = "用户注册时间")
     @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "账号状态 0禁用  1正常 ")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
     @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "个人简介")
    private String about;

    @ApiModelProperty(value = "角色id")
    private Integer  roleid;
}
