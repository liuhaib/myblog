package com.liuhaibin.myblog.controller;

import com.liuhaibin.myblog.service.BlogInfoService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HaiBin
 * @Description: 博客信息
 * @Auther: HaiBin
 * @Date: 2023/2/25/0025 15:08
 * @Version 1.0
 */
@RestController
public class BlogInfoController {

    @Resource
    BlogInfoService blogInfoService;

    @GetMapping("/blogInfo/getBlogInfo")
    public Result getBlogHomeInfo(){
        return blogInfoService.getBlogHomeInfo();
    }
}
