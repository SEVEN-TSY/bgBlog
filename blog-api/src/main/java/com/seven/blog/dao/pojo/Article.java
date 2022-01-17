package com.seven.blog.dao.pojo;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/3 21:54
 * @Description article
 */
@Data
public class Article {
    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight = Article_Common;


    /**
     * 创建时间
     */
    private Long createDate;
}
