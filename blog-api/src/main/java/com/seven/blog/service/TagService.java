package com.seven.blog.service;

import com.seven.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    /*
     * 根据文章id获取标签
     * @param id
     * @date 2021/12/22 17:47
     * @return List<TagVo>
     */
    List<TagVo> findTagsByArticleId(Long id);

    /*
     * 获取最热标签
     * @param limit
     * @date 2021/12/22 17:47
     * @return List<TagVo>
     */
    List<TagVo> getHotTag(int limit);

    /*
     * 写文章-获取所有标签
     * @date 2021/12/22 17:47
     * @return List<TagVo>
     */
    List<TagVo> getAllTags();
}
