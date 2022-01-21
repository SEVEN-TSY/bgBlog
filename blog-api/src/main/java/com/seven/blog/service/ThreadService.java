package com.seven.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.blog.common.aop.LogAnnotation;
import com.seven.blog.dao.mapper.ArticleMapper;
import com.seven.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/21 20:25
 * @Description
 */
@Component
public class ThreadService {

    @Async("taskExecutor")
    public void updateViewCounts(ArticleMapper articleMapper,Article article){
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        Article updateArticle=new Article();
        updateArticle.setViewCounts(article.getViewCounts()+1);
        lambdaQueryWrapper.eq(Article::getId,article.getId());
        lambdaQueryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(updateArticle,lambdaQueryWrapper);

    }
    @Async("taskExecutor")
    public void updateCommentCounts(ArticleMapper articleMapper,Long articleId){
        Article article = articleMapper.selectById(articleId);
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        Article updateArticle=new Article();
        updateArticle.setCommentCounts(article.getCommentCounts()+1);
        lambdaQueryWrapper.eq(Article::getId,articleId);
        lambdaQueryWrapper.eq(Article::getCommentCounts,article.getCommentCounts());
        articleMapper.update(updateArticle,lambdaQueryWrapper);

    }
}
