package com.seven.blog.service;

import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.PageParams;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    //列出分页的文章列表
    Result listArticlesPage(PageParams pageParams);
    //列出最热文章
    Result listHotArticles(int limit);
    //最新文章
    Result listNewArticle(int limit);
    //文章归档
    Result listArchives();

    //根据文章id获取文章信息
    Result findArticleById(Long id);
}
