package com.liuhaibin.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhaibin.myblog.dto.LabelOptionDTO;
import com.liuhaibin.myblog.dto.MenuDTO;
import com.liuhaibin.myblog.pojo.SysMenu;
import com.liuhaibin.myblog.uaits.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
public interface SysMenuService extends IService<SysMenu> {

    Result GetMenuById(Long id);

    List<MenuDTO> listMenus(String keywords);

    Result AddorUpdate(MenuDTO menuDTO);

    List<LabelOptionDTO> listMenuOptions();
}
