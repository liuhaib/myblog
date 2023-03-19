package com.liuhaibin.myblog.controller;


import com.liuhaibin.myblog.dto.ArticleDTO;
import com.liuhaibin.myblog.dto.ArticleTopDTO;
import com.liuhaibin.myblog.dto.ConditionDTO;
import com.liuhaibin.myblog.dto.DeleteDTO;
import com.liuhaibin.myblog.service.SysArticleService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  文章控制器
 * </p>
 *T
 * @author haibin
 * @since 2022-10-11
 */
@RestController
public class SysArticleController {

    @Resource
    SysArticleService sysArticleService;


    //新增或者修改文章
    @PostMapping("/sysArticle/articles")
    public Result saveOrUpdateArticle(@Valid @RequestBody ArticleDTO articleDTO){
        return sysArticleService.saveOrUpdateArticle(articleDTO);
    }
    //查看后台文章
    @GetMapping("/sysArticle/articles")
    public Result listArticleBacks(ConditionDTO conditionDTO){
        return sysArticleService.listArticleBacks(conditionDTO);
    }
    //根据id查看后台文章
    @GetMapping("/sysArticle/articles/{articleId}")
    public Result getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return sysArticleService.getArticleBackById(articleId);
    }
    //上传文章图片
    @PostMapping("/sysArticle/articles/images")
    public Result saveArticleImages(MultipartFile file) throws IOException {
        return Result.success(sysArticleService.saveArticleImages(file));
    }
    //修改文章置顶状态
    @PutMapping("/sysArticle/articles/top")
    public Result updateArticleTop(@Valid @RequestBody ArticleTopDTO articleTopDTO){
        return sysArticleService.updateArticleTop(articleTopDTO);
    }
    //逻辑删除
    @PutMapping("/sysArticle/articles")
    public Result updateArticleDelete (@Valid @RequestBody DeleteDTO deleteDTO){
        return sysArticleService.updateArticleDelete(deleteDTO);
    }
    //物理删除
    @DeleteMapping("/sysArticle/articles")
    public Result  deleteArticles (@RequestBody List<Integer> ids){
        return sysArticleService.deleteArticles(ids);
    }
}

