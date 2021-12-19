package com.seven.blog.controller;

import com.seven.blog.service.ArticleService;
import com.seven.blog.vo.params.PageParams;
import com.seven.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 9:42
 * @Description
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /*
     * 获取文章列表
     * @param pageParams
     * @date 2021/12/4 15:19
     * @return Result
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticlesPage(pageParams);

    }
    /*
     * 获取最热文章
     * @date 2021/12/13 19:49
     * @return Result
     */
    @PostMapping("hot")
    public Result getHotArticle(){
        int limit=5;
        return articleService.listHotArticles(limit);
    }
    /*
     * 获取最新文章
     * @date 2021/12/13 20:03
     * @return Result
     */
    @PostMapping("new")
    public Result getNewArticle(){
        int limit=5;
        return articleService.listNewArticle(limit);
    }
    /*
     * 文章归档
     * @date 2021/12/13 20:48
     * @return Result
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /*
     * 获取文章详情
     * @date 2021/12/19 15:16
     * @return Result
     */
    @PostMapping("view/{id}")
    public Result getArticleView(@PathVariable("id") Long id){

        return articleService.findArticleById(id);
    }



}
