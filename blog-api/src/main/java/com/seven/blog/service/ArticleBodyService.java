package com.seven.blog.service;

import com.seven.blog.vo.ArticleBodyVo;
import com.seven.blog.vo.params.ArticleBodyParams;

public interface ArticleBodyService {

    ArticleBodyVo getBodyVoById(Long bodyId);

    /*
     * 添加文章主体并返回body id
     * @param body
     * @date 2021/12/30 19:11
     * @return Long
     */
    Long insertBody(Long articleId,ArticleBodyParams body);
}
