package com.liuhaibin.myblog.mapper;

import com.liuhaibin.myblog.pojo.SysTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
public interface SysTagMapper extends BaseMapper<SysTag> {

    List<Integer> listTagNameByArticleId(@Param("articleId") Integer articleId);

}
