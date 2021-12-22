package com.seven.blog.service;

import com.seven.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);

    List<TagVo> getHotTag(int limit);

    List<TagVo> getAllTags();
}
