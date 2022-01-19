package com.seven.blog.service;

import com.seven.blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    /*
     * 根据类别id获取分类信息
     * @param categoryId
     * @date 2021/12/22 17:44
     * @return List<CategoryVo>
     */
    List<CategoryVo> getCategoryVoById(Long categoryId);

    /*
     * 写文章-获取所有分类信息
     * @date 2021/12/22 17:44
     * @return List<CategoryVo>
     */
    List<CategoryVo> getAllCategories();

    /*
     * 获取导航栏所有分类信息+description
     * @date 2022/1/18 16:02
     * @return List<CategoryVo>
     */
    List<CategoryVo> getAllCategoriesDetail();

    List<CategoryVo> getCategoriesDetailById(Long id);
}
