package com.liuhaibin.myblog.controller;


import com.liuhaibin.myblog.service.SysResourceService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  资源控制器
 * </p>
 *
 * @author haibin
 * @since 2022-05-12
 */
@RestController
public class SysResourceController {

    @Resource
    SysResourceService sysResourceService;

    @GetMapping("/resource/resources")
    public Result GetResources(){
        return null;
    }



}

