package com.seven.blog.controller;

import com.seven.blog.service.CommentService;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/21 21:37
 * @Description
 */
@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /*
     * 获取文章评论数据列表
     * @param articleId
     * @date 2021/12/22 16:26
     * @return Result
     */
    @GetMapping("article/{id}")
    public Result getComments(@PathVariable("id") Long articleId){
        return Result.success(commentService.getComments(articleId));
    }

    /*
     * 评论文章
     * @param commentParams
     * @date 2021/12/22 16:29
     * @return Result
     */
    @PostMapping("create/change")
    public Result makeComment(@RequestBody CommentParams commentParams){
        int row= commentService.makeComment(commentParams);
        return Result.success(row);
    }
}
