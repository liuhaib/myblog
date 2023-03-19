package com.liuhaibin.myblog.service;

import com.liuhaibin.myblog.dto.CategoryDTO;
import com.liuhaibin.myblog.pojo.SysCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhaibin.myblog.uaits.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
public interface SysCategoryService extends IService<SysCategory> {

    Result listBackCategories(Integer pageNum, Integer pageSize, String keywords);

    Result listCategoriesDTO();

    Result addAndUpdateCategories(CategoryDTO categoryDTO);

    Result DeleteCategories(List<Integer> ids);
}
