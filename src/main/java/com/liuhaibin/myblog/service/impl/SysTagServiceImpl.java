package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.dto.TagDTO;
import com.liuhaibin.myblog.exception.BizException;
import com.liuhaibin.myblog.mapper.SysArticleTagMapper;
import com.liuhaibin.myblog.mapper.SysTagMapper;
import com.liuhaibin.myblog.pojo.SysArticleTag;
import com.liuhaibin.myblog.pojo.SysTag;
import com.liuhaibin.myblog.service.SysTagService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
@Service
public class SysTagServiceImpl extends ServiceImpl<SysTagMapper, SysTag> implements SysTagService {

    @Resource
    SysArticleTagMapper sysArticleTagMapper;

    @Override
    public Result listTags() {
        return null;
    }

    @Override
    public Result listTagsBack(Integer pageNum, Integer pageSize, String keywords) {
        Page<SysTag> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysTag> wrapper = new QueryWrapper<>();
        if (!"".equals(keywords)) {
            wrapper.like("tag_name", keywords);
        }
        wrapper.orderByDesc("id");
        Page<SysTag> tagPage = page(page, wrapper);
        return Result.success(tagPage);
    }

    @Override
    public Result saveOrUpdateTag(TagDTO tagDTO) {
        if (BeanUtil.isEmpty(tagDTO.getTagName())) {
            return Result.error("标签名不能为空！");
        }

        QueryWrapper<SysTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_name",tagDTO.getTagName());
        SysTag tag = getOne(queryWrapper);

        if (Objects.nonNull(tag) && !tagDTO.getId().equals(tag.getId())) {
            return Result.error("标签名已经存在！");
        }

        SysTag sysTag = BeanUtil.copyProperties(tagDTO, SysTag.class);
        Date date = new Date();
        sysTag.setCreateTime(date);

        saveOrUpdate(sysTag);
        return Result.success("操作成功！");
    }

    @Override
    public Result listTagsDTO() {
        List<SysTag> sysTags = list(null);
        List<TagDTO> tagDTOList = BeanUtil.copyToList(sysTags, TagDTO.class);
        return Result.success(tagDTOList);
    }

    @Override
    public Result deleteTags(List<Integer> ids) {
        Integer count = sysArticleTagMapper.selectCount(new LambdaQueryWrapper<SysArticleTag>()
                .in(SysArticleTag::getTagId, ids));
        if (count > 0) {
            return Result.error("删除失败，该标签下存在文章");
        }
        this.baseMapper.deleteBatchIds(ids);
        return Result.success();
    }

}
