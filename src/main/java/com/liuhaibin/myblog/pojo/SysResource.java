package com.liuhaibin.myblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author haibin
 * @since 2022-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysResource对象", description="")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "资源名")
    private String resourceName;

    @ApiModelProperty(value = "权限路径")
    private String url;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "父权限id")
    private Long parentId;

    @ApiModelProperty(value = "是否匿名访问 0否 1是")
    private Boolean isAnonymous;

    @ApiModelProperty(value = "创建时间")
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
