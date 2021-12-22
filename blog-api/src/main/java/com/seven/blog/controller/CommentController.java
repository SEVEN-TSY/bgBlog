package com.seven.blog.controller;

import com.seven.blog.service.CommentService;
import com.seven.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("article/{id}")
    public Result getComments(@PathVariable("id") Long articleId){
        return Result.success(commentService.getComments(articleId));
    }
}
