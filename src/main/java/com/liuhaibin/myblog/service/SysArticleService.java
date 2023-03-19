package com.liuhaibin.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhaibin.myblog.dto.ArticleDTO;
import com.liuhaibin.myblog.dto.ArticleTopDTO;
import com.liuhaibin.myblog.dto.ConditionDTO;
import com.liuhaibin.myblog.dto.DeleteDTO;
import com.liuhaibin.myblog.pojo.SysArticle;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haibin
 * @since 2022-10-11
 */
public interface SysArticleService extends IService<SysArticle> {



    Result saveOrUpdateArticle(ArticleDTO articleDTO);

    Result saveArticleImages(MultipartFile file) throws IOException;

    Result listArticleBacks(ConditionDTO conditionDTO);

    Result getArticleBackById(Integer articleId);

    Result updateArticleTop(ArticleTopDTO articleTopDTO);

    Result updateArticleDelete(DeleteDTO deleteDTO);

    Result deleteArticles(List<Integer> ids);
}
