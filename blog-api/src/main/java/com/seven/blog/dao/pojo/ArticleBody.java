package com.seven.blog.dao.pojo;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/19 15:48
 * @Description 文章主体内容
 */
@Data
public class ArticleBody {
    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;
}
