package com.seven.blog.controller;

import com.seven.blog.dao.pojo.Category;
import com.seven.blog.service.CategoryService;
import com.seven.blog.vo.CategoryVo;
import com.seven.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/22 17:23
 * @Description
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*
     * 写文章-获取所有分类
     * @date 2021/12/22 17:41
     * @return Result
     */
    @GetMapping
    public Result getAllCategories(){
        List<CategoryVo> categoryVoList= categoryService.getAllCategories();
        return Result.success(categoryVoList);
    }

    /*
     * 导航获取所有分类详细信息
     * @date 2022/1/18 16:11
     * @return Result
     */
    @GetMapping("/detail")
    public Result getCategoryDetails(){
        List<CategoryVo> categoryVoList= categoryService.getAllCategoriesDetail();
        return Result.success(categoryVoList);
    }
}
