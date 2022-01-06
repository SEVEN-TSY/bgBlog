package com.seven.blog.service.impl;

import com.seven.blog.dao.mapper.ArticleBodyMapper;
import com.seven.blog.dao.pojo.ArticleBody;
import com.seven.blog.service.ArticleBodyService;
import com.seven.blog.vo.ArticleBodyVo;
import com.seven.blog.vo.params.ArticleBodyParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/19 16:52
 * @Description
 */
@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo getBodyVoById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public Long insertBody(Long articleId,ArticleBodyParams body) {
        ArticleBody articleBody=new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(body.getContent());
        articleBody.setContentHtml(body.getContentHtml());
        articleBodyMapper.insert(articleBody);
        return articleBody.getId();
    }
}
