package com.liuhaibin.myblog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysArticle对象", description="")
public class SysArticle implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作者")
    private Integer userId;

    @ApiModelProperty(value = "文章分类")
    private Integer categoryId;

    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    @ApiModelProperty(value = "标题")
    private String articleTitle;

    @ApiModelProperty(value = "内容")
    private String articleContent;

    @ApiModelProperty(value = "原文链接")
    private String originalUrl;

    @ApiModelProperty(value = "是否置顶 0否 1是")
    private Boolean isTop;

    @ApiModelProperty(value = "是否删除  0否 1是")
    private Boolean isDelete;

    @ApiModelProperty(value = "是否草稿  0否 1是")
    private Boolean isDraft;

    @ApiModelProperty(value = "文章状态 1公开 2私密")
    private Integer status;

    @ApiModelProperty(value = "发表时间")
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
