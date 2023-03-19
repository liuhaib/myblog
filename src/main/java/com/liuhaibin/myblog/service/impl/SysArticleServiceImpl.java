package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.anth.MyUserDetails;
import com.liuhaibin.myblog.dto.*;
import com.liuhaibin.myblog.mapper.SysArticleMapper;
import com.liuhaibin.myblog.mapper.SysCategoryMapper;
import com.liuhaibin.myblog.mapper.SysTagMapper;
import com.liuhaibin.myblog.pojo.SysArticle;
import com.liuhaibin.myblog.pojo.SysCategory;
import com.liuhaibin.myblog.service.SysArticleService;
import com.liuhaibin.myblog.service.SysFileService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper, SysArticle> implements SysArticleService {

    @Resource
    SysFileService sysFileService;

    @Resource
    SysCategoryMapper sysCategoryMapper;

    @Resource
    SysTagMapper sysTagMapper;


    @Transactional
    @Override
    public Result saveOrUpdateArticle(ArticleDTO articleDTO) {
        //获取文章所有标签
        List<Integer> tagIdList = articleDTO.getTagIdList();
        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
        //赋值对象
        SysArticle sysArticle = BeanUtil.copyProperties(articleDTO, SysArticle.class);
        sysArticle.setUserId(myUserDetails.getSysUser().getId().intValue());
        //判断是添加还是修改
        if(BeanUtil.isEmpty(sysArticle.getId())){
            //新增
            sysArticle.setCreateTime(DateUtil.date());
            saveArticle(sysArticle,tagIdList);
        }else {
            //修改
            sysArticle.setUpdateTime(DateUtil.date());
            upDateArticle(sysArticle,tagIdList);
        }
        return Result.success();
    }

    /**
     * @Description  新增文章
     * @author HaiBin
     * @date    2022/11/26 15:56
     * @params  [sysArticle, tagIdList]
     * @return  void
     */
    public void saveArticle(SysArticle sysArticle,List<Integer> tagIdList){
        //判断 标签id是否 存在
        if (tagIdList.isEmpty()) {
            //不存在 直接保存
            save(sysArticle);
        }else {
            //存在标签
            //保存 并获取文章id
            baseMapper.insert(sysArticle);
            //新增关系表
            baseMapper.saveArticleTag(sysArticle.getId(),tagIdList);
        }
    }

    /**
     * @Description  修改文章
     * @author HaiBin
     * @date    2022/11/26 15:56
     * @params  [sysArticle, tagIdList]
     * @return  void
     */
    public void upDateArticle(SysArticle sysArticle,List<Integer> tagIdList){
        //根据id 删除关系表
        baseMapper.deleteTagById(sysArticle.getId());
        //修改 文章
        updateById(sysArticle);
        //新增关系表
        baseMapper.saveArticleTag(sysArticle.getId(),tagIdList);
    }

    @Override
    public Result saveArticleImages(MultipartFile file) throws IOException {
        String url = sysFileService.updataFlie(file);
        return Result.success(url);
    }

    @Override
    public Result listArticleBacks(ConditionDTO conditionDTO) {
        Page<ArticleBackDTO> page = new Page<>(conditionDTO.getPageNum(),conditionDTO.getPageSize());
        IPage<ArticleBackDTO> listArticleBacks = this.baseMapper.listArticleBacks(page, conditionDTO);
        return Result.success(listArticleBacks);
    }

    @Override
    public Result getArticleBackById(Integer articleId) {
        // 查询文章数据
        SysArticle sysArticle = getById(articleId);
        // 查询分类数据
        SysCategory sysCategory = sysCategoryMapper.selectById(sysArticle.getCategoryId());
        // 查询文章标签
        List<Integer> tagNameList = sysTagMapper.listTagNameByArticleId(articleId);
        // 封装数据
        ArticleDTO articleDTO = BeanUtil.copyProperties(sysArticle, ArticleDTO.class);
        if(!ObjectUtil.isNull(sysCategory)) articleDTO.setCategoryId(sysCategory.getId());
        if(!ObjectUtil.isNull(tagNameList)) articleDTO.setTagIdList(tagNameList);
        return Result.success(articleDTO);
    }

    @Override
    public Result updateArticleTop(ArticleTopDTO articleTopDTO) {
        UpdateWrapper<SysArticle> wrapper = new UpdateWrapper<>();
        wrapper.set("is_top",articleTopDTO.getIsTop());
        wrapper.eq("id",articleTopDTO.getId());
        update(wrapper);
        return Result.success();
    }

    @Override
    public Result updateArticleDelete(DeleteDTO deleteDTO) {
        UpdateWrapper<SysArticle> wrapper = new UpdateWrapper<>();
        wrapper.set("is_delete",deleteDTO.getIsDelete());
        wrapper.in("id",deleteDTO.getIdList());
        update(wrapper);
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteArticles(List<Integer> ids) {
        // 删除文章标签关联
        this.baseMapper.deleteArticlesTag(ids);
        // 删除文章
        this.baseMapper.deleteBatchIds(ids);
        return Result.success();
    }
}
