package com.liuhaibin.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhaibin.myblog.pojo.SysMenu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> ByidGetmenu(Long id);

}
