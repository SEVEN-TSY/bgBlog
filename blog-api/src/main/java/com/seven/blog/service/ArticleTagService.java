package com.seven.blog.service;

import com.seven.blog.vo.TagVo;

import java.util.List;

public interface ArticleTagService {
    void insertArticleTag(Long articleId,List<TagVo> tags);
}
