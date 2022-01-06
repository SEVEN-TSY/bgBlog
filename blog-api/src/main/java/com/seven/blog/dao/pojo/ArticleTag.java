package com.seven.blog.dao.pojo;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/30 18:49
 * @Description
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
