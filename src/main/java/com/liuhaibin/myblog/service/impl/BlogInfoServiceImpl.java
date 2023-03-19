package com.liuhaibin.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuhaibin.myblog.dto.BlogHomeInfoDTO;
import com.liuhaibin.myblog.mapper.SysArticleMapper;
import com.liuhaibin.myblog.mapper.SysCategoryMapper;
import com.liuhaibin.myblog.mapper.SysTagMapper;
import com.liuhaibin.myblog.mapper.SysUserMapper;
import com.liuhaibin.myblog.pojo.SysArticle;
import com.liuhaibin.myblog.pojo.SysUser;
import com.liuhaibin.myblog.service.BlogInfoService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author HaiBin
 * @Description:
 * @Auther: HaiBin
 * @Date: 2023/2/25/0025 15:38
 * @Version 1.0
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysArticleMapper sysArticleMapper;
    @Resource
    SysTagMapper sysTagMapper;
    @Resource
    SysCategoryMapper sysCategoryMapper;

    @Override
    public Result getBlogHomeInfo() {
        //获取用户信息
        SysUser sysUser = sysUserMapper.selectById(1);
        //获取文章数量
        Integer articleCount = sysArticleMapper.selectCount(new LambdaQueryWrapper<SysArticle>()
                .eq(SysArticle::getStatus, 1)
                .eq(SysArticle::getIsDelete, 0));
        //获取标签数量
        Integer tagCount = sysTagMapper.selectCount(null);
        //获取分类数量
        Integer categoryCount = sysCategoryMapper.selectCount(null);

        //封装信息
        BlogHomeInfoDTO blogHomeInfoDTO = new BlogHomeInfoDTO(
                sysUser.getHeadportrait(),
                sysUser.getNickname(),
                sysUser.getAbout(),
                articleCount,
                categoryCount,
                tagCount,
                "520");

        return Result.success(blogHomeInfoDTO);
    }
}
