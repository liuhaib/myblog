package com.liuhaibin.myblog.controller;


import com.liuhaibin.myblog.dto.CategoryDTO;
import com.liuhaibin.myblog.service.SysCategoryService;
import com.liuhaibin.myblog.uaits.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  分类控制器
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
@RestController
public class SysCategoryController {

    @Resource
    SysCategoryService sysCategoryService;

    @ApiOperation(value = "查看分类列表")
    @GetMapping("public/categories")
    public Result listCategories(){
        return null;
    }


    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/sysCategory/categories")
    public Result listBackCategories(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String keywords){
        return sysCategoryService.listBackCategories(pageNum,pageSize,keywords);
    }

    @ApiOperation(value = "查看文章分类列表")
    @GetMapping("/sysCategory/categoriesDTO")
    public Result listCategoriesDTO(){
        return sysCategoryService.listCategoriesDTO();
    }


    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/sysCategory/categories")
    public Result addAndUpdateCategories(@RequestBody CategoryDTO categoryDTO){
        return sysCategoryService.addAndUpdateCategories(categoryDTO);
    }


    @ApiOperation(value = "删除分类")
    @DeleteMapping("/sysCategory/categories")
    public Result deleteCategories(@RequestBody List<Integer> ids){
        return sysCategoryService.DeleteCategories(ids);
    }

}

