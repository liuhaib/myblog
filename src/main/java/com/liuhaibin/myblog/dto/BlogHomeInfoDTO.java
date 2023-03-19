package com.liuhaibin.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 博客首页信息
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoDTO {

    /**
     *头像
     */
    private String avatar;

    /**
     *昵称
     */
    private String  nickname;

    /**
     *公告
     */
    private String intro;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

    /**
     * 访问量
     */
    private String viewsCount;


}
