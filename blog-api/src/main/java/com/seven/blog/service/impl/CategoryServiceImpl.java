package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.blog.dao.mapper.CategoryMapper;
import com.seven.blog.dao.pojo.Article;
import com.seven.blog.dao.pojo.Category;
import com.seven.blog.service.CategoryService;
import com.seven.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/19 16:49
 * @Description
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> getCategoryVoById(Long categoryId) {

        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getId,categoryId);
        List<Category> categories = categoryMapper.selectList(lambdaQueryWrapper);
        return copyList(categories);
    }

    @Override
    public List<CategoryVo> getAllCategories() {
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(categoryLambdaQueryWrapper);
        return copyList(categories);
    }

    @Override
    public List<CategoryVo> getAllCategoriesDetail() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return copyList(categories);
    }

    private List<CategoryVo> copyList(List<Category> categoryList) {
        List<CategoryVo> categoryVoList =new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryVoList.add(categoryVo);
        }
        return categoryVoList;
    }
}
