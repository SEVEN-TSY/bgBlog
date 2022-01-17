package com.seven.blog.service.impl;

import com.seven.blog.dao.mapper.ArticleTagMapper;
import com.seven.blog.dao.pojo.ArticleTag;
import com.seven.blog.service.ArticleTagService;
import com.seven.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/30 18:57
 * @Description
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public void insertArticleTag(Long articleId, List<TagVo> tags) {
        if (tags!=null){
            for (TagVo tag : tags) {
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }

    }
}
