package com.seven.blog.service;

import com.seven.blog.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleTagService {
    void insertArticleTag(Long articleId,List<TagVo> tags);
}
