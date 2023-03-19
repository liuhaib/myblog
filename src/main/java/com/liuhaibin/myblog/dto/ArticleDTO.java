package com.liuhaibin.myblog.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Description:  后台编辑文章
 * @Auther: HaiBin
 * @Date: 2022/11/15 14:25
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    /**
     * 文章id
     */
    @ApiModelProperty(name = "id", value = "文章id", dataType = "Integer")
    private Integer id;

    /**
     * 标题
     */
    @NotBlank(message = "文章标题不能为空")
    @ApiModelProperty(name = "articleTitle", value = "文章标题", required = true, dataType = "String")
    private String articleTitle;

    /**
     * 内容
     */
    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(name = "articleContent", value = "文章内容", required = true, dataType = "String")
    private String articleContent;

    /**
     * 文章封面
     */
    @ApiModelProperty(name = "articleCover", value = "文章缩略图", dataType = "String")
    private String articleCover;

    /**
     * 文章分类
     */
    @ApiModelProperty(name = "categoryId", value = "文章分类", dataType = "Integer")
    private Integer categoryId;

    /**
     * 文章标签
     */
    @ApiModelProperty(name = "tagIdList", value = "文章标签", dataType = "List<Integer>")
    private List<Integer> tagIdList;

    /**
     * 文章类型
     */
    @ApiModelProperty(name = "type", value = "文章类型", dataType = "Integer")
    private Integer type;

    /**
     * 原文链接
     */
    @ApiModelProperty(name = "originalUrl", value = "原文链接", dataType = "String")
    private String originalUrl;

    /**
     * 是否置顶
     */
    @ApiModelProperty(name = "isTop", value = "是否置顶", dataType = "Integer")
    private Integer isTop;

    /**
     * 是否草稿
     */
    @ApiModelProperty(name = "isDraft", value = "草稿", dataType = "Integer")
    private Integer isDraft;

    /**
     * 文章状态
     */
    @ApiModelProperty(name = "status", value = "文章状态", dataType = "Integer")
    private Integer status;
}
