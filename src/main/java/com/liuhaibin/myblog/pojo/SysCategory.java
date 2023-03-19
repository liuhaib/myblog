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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysCategory对象", description="")
public class SysCategory implements Serializable {

    private static final long serialVersionUID = 1L;

     @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "创建时间")
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
