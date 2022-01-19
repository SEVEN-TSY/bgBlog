package com.seven.blog.controller;

import com.seven.blog.service.TagService;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /*
     * 导航-获取所有标签详细信息
     * @date 2022/1/18 16:14
     * @return Result
     */
    @GetMapping("detail")
    public Result getAllTagsDetail(){
        List<TagVo> allTags = tagService.getAllTagsDetail();
        return Result.success(allTags);
    }

    @GetMapping("detail/{id}")
    public Result getTagsDetailById(@PathVariable("id") Long id){
        List<TagVo> allTags = tagService.getTagsDetailById(id);
        return Result.success(allTags);
    }
}
