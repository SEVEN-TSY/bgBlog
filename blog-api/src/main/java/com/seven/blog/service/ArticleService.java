package com.seven.blog.service;

import com.seven.blog.dao.dos.Archives;
import com.seven.blog.vo.ArticleVo;
import com.seven.blog.vo.params.PageParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    //列出分页的文章列表
    List<ArticleVo> listArticlesPage(PageParams pageParams);

    //列出最热文章
    List<ArticleVo> listHotArticles(int limit);

    //最新文章
    List<ArticleVo> listNewArticle(int limit);

    //文章归档
    List<Archives> listArchives();

    //根据文章id获取文章信息
    ArticleVo findArticleById(Long id);
}
