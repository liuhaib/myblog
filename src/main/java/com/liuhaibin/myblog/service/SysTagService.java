package com.liuhaibin.myblog.service;

import com.liuhaibin.myblog.dto.TagDTO;
import com.liuhaibin.myblog.pojo.SysTag;
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
public interface SysTagService extends IService<SysTag> {

    Result listTags();

    Result listTagsBack(Integer pageNum, Integer pageSize, String keywords);

    Result saveOrUpdateTag(TagDTO tagDTO);

    Result listTagsDTO();

    Result deleteTags(List<Integer> ids);
}
