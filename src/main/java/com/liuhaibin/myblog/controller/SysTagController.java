package com.liuhaibin.myblog.controller;


import com.liuhaibin.myblog.dto.TagDTO;
import com.liuhaibin.myblog.service.SysTagService;
import com.liuhaibin.myblog.uaits.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  标签控制器
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
@RestController
public class SysTagController {

    @Resource
    SysTagService sysTagService;

    @ApiOperation(value = "查询标签列表")
    @GetMapping("/public/tags")
    public Result listTags(){
        return sysTagService.listTags();
    }


    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/tag/tags")
    public Result listTagsBack(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String keywords){
        return sysTagService.listTagsBack(pageNum,pageSize,keywords);
    }


    @ApiOperation(value = "文章查询标签列表")
    @GetMapping("/tag/tagDTO")
    public Result listTagsDTO(){
        return sysTagService.listTagsDTO();
    }


    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/tag/tags")
    public Result saveOrUpdateTag(@RequestBody TagDTO tagDTO){
        return sysTagService.saveOrUpdateTag(tagDTO);
    }


    @ApiOperation(value = "删除标签")
    @DeleteMapping("/tag/tags")
    public Result deleteTags(@RequestBody List<Integer> ids){
        return sysTagService.deleteTags(ids);
    }


}

