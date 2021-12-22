package com.seven.blog.service;

import com.seven.blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    List<CategoryVo> getCategoryVoById(Long categoryId);

    List<CategoryVo> getAllCategories();
}
