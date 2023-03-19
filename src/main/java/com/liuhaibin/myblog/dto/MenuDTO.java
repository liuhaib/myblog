package com.liuhaibin.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否隐藏
     */
    private Integer isHidden;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 子菜单列表
     */
    private List<MenuDTO> children;

}
