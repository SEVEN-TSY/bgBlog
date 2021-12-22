package com.seven.blog.controller;

import com.seven.blog.service.TagService;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/8 19:45
 * @Description 标签接口
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /*
     * 获取最热标签
     * @date 2021/12/13 21:52
     * @return Result
     */
    @PostMapping("/hot")
    public Result getHotTag(){
        int limit=6;
        List<TagVo> hotTag = tagService.getHotTag(limit);
        return Result.success(hotTag);
    }
    /*
     * 写文章-获取所有标签
     * @date 2021/12/22 17:41
     * @return Result
     */
    @GetMapping
    public Result getAllTags(){
        List<TagVo> allTags = tagService.getAllTags();
        return Result.success(allTags);
    }
}
