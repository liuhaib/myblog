package com.liuhaibin.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色选项DTO
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色简介
     */
    private String roleLabel;

}
