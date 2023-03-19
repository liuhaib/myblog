package com.liuhaibin.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liuhaibin.myblog.dto.ArticleBackDTO;
import com.liuhaibin.myblog.dto.ConditionDTO;
import com.liuhaibin.myblog.pojo.SysArticle;
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
public interface SysArticleMapper extends BaseMapper<SysArticle> {

    IPage<ArticleBackDTO> listArticleBacks(IPage<ArticleBackDTO> page, @Param("condition") ConditionDTO condition);

    int saveArticleTag(@Param("articleId") Integer articleId, @Param("tagIdList")List<Integer> tagIdList);

    int deleteTagById(@Param("articleId") Integer articleId);

    int deleteArticlesTag(@Param("ids") List<Integer> ids);
}
