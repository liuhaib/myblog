package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.liuhaibin.myblog.dto.LabelOptionDTO;
import com.liuhaibin.myblog.dto.MenuDTO;
import com.liuhaibin.myblog.dto.UserMenuDTO;
import com.liuhaibin.myblog.pojo.SysMenu;
import com.liuhaibin.myblog.mapper.SysMenuMapper;
import com.liuhaibin.myblog.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.uaits.BeanCopyUtil;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public Result GetMenuById(Long id) {
        // 查询用户菜单信息
        List<SysMenu> menuList = this.baseMapper.ByidGetmenu(id);
        // 获取目录列表
        List<SysMenu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Long, List<SysMenu>> childrenMap = getMenuMap(menuList);
        // 转换前端菜单格式
        List<UserMenuDTO> userMenuDTOS = convertUserMenuList(catalogList, childrenMap);
        return Result.success(userMenuDTOS);
    }

    @Override
    public List<MenuDTO> listMenus(String keywords) {
        // 查询菜单数据
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StringUtils.isNotBlank(keywords), SysMenu::getName, keywords));
        // 获取目录列表
        List<SysMenu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Long, List<SysMenu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtil.copyObject(item, MenuDTO.class);
            // 获取目录下的菜单排序
            List<MenuDTO> list = BeanCopyUtil.copyList(childrenMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(list);
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());
    }

    @Override
    public Result AddorUpdate(MenuDTO menuDTO) {
        SysMenu sysMenu = BeanUtil.copyProperties(menuDTO, SysMenu.class);
        if(sysMenu.getCreateTime() == null){
            sysMenu.setCreateTime(new Date());
        }else {
            sysMenu.setUpdateTime(new Date());
        }
        if (!saveOrUpdate(sysMenu)) {
            return Result.error("操作失败");
        }
        return Result.success();
    }

    @Override
    public List<LabelOptionDTO> listMenuOptions() {
        // 查询菜单数据
        List<SysMenu> menuList = this.baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .select(SysMenu::getId, SysMenu::getName, SysMenu::getParentId, SysMenu::getOrderNum));
        // 获取目录列表
        List<SysMenu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Long, List<SysMenu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            // 获取目录下的菜单排序
            List<LabelOptionDTO> list = new ArrayList<>();
            List<SysMenu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(SysMenu::getOrderNum))
                        .map(menu -> LabelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }


    /**
     * 获取目录列表
     *
     * @param menuList 菜单列表
     * @return 目录列表
     */
    private List<SysMenu> listCatalog(List<SysMenu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .collect(Collectors.toList());
    }

    /**
     * 获取目录下菜单列表
     *
     * @param menuList 菜单列表
     * @return 目录下的菜单列表
     */
    private Map<Long, List<SysMenu>> getMenuMap(List<SysMenu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(SysMenu::getParentId));
    }

    /**
     * 转换用户菜单格式
     *
     * @param catalogList 目录
     * @param childrenMap 子菜单
     */
    private List<UserMenuDTO> convertUserMenuList(List<SysMenu> catalogList, Map<Long, List<SysMenu>> childrenMap) {
        return catalogList.stream().map(item -> {
            // 获取目录
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            // 获取目录下的子菜单
            List<SysMenu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                // 多级菜单处理
                userMenuDTO = BeanCopyUtil.copyObject(item, UserMenuDTO.class);
                list = children.stream()
                        .sorted(Comparator.comparing(SysMenu::getOrderNum))
                        .map(menu -> {
                            UserMenuDTO dto = BeanCopyUtil.copyObject(menu, UserMenuDTO.class);
                            dto.setHidden(menu.getIsHidden().equals(1));
                            return dto;
                        }).collect(Collectors.toList());
            } else {
                // 一级菜单处理
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent("Layout");
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getIsHidden().equals(1));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }
}
