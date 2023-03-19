package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.dto.CategoryDTO;
import com.liuhaibin.myblog.mapper.SysArticleMapper;
import com.liuhaibin.myblog.mapper.SysCategoryMapper;
import com.liuhaibin.myblog.pojo.SysArticle;
import com.liuhaibin.myblog.pojo.SysCategory;
import com.liuhaibin.myblog.service.SysCategoryService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
@Service
public class SysCategoryServiceImpl extends ServiceImpl<SysCategoryMapper, SysCategory> implements SysCategoryService {

    @Resource
    private SysArticleMapper sysArticleMapper;

    @Override
    public Result listBackCategories(Integer pageNum, Integer pageSize, String keywords) {
        Page<SysCategory> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysCategory> queryWrapper = new QueryWrapper<>();
        if (!"".equals(keywords)) {
            queryWrapper.like("category_name", keywords);
        }
        queryWrapper.orderByDesc("id");
        Page<SysCategory> tagPage = page(page, queryWrapper);
        return Result.success(tagPage);
    }

    @Override
    public Result listCategoriesDTO() {
        List<SysCategory> categoryList = list(null);
        List<CategoryDTO> categoryDTOS = BeanUtil.copyToList(categoryList, CategoryDTO.class);
        return Result.success(categoryDTOS);
    }

    @Override
    public Result addAndUpdateCategories(CategoryDTO categoryDTO) {
        List<SysCategory> sysCategoryList = list();
        for (SysCategory sysCategory : sysCategoryList) {
           if(categoryDTO.getCategoryName().equals(sysCategory.getCategoryName())){
               return Result.error("该分类已经存在！");
           }
        }
        SysCategory sysCategory = BeanUtil.copyProperties(categoryDTO, SysCategory.class);
        if(sysCategory.getId() == null){
            sysCategory.setCreateTime(DateUtil.date());
        }else {
            sysCategory.setUpdateTime(DateUtil.date());
        }
        saveOrUpdate(sysCategory);
        return Result.success();
    }

    @Override
    public Result DeleteCategories(List<Integer> ids) {
        Integer count = sysArticleMapper.selectCount(new LambdaQueryWrapper<SysArticle>()
                .in(SysArticle::getCategoryId, ids));
        if (count > 0) {
            return Result.error("删除失败，该分类下存在文章");
        }
        this.baseMapper.deleteBatchIds(ids);
        return Result.success();
    }
}
