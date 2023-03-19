package com.liuhaibin.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:  分类返回
 * @Auther: HaiBin
 * @Date: 2022/11/15 08:25
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;
}
