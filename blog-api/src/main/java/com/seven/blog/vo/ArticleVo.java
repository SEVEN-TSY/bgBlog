package com.seven.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 10:18
 * @Description Article前台数据
 */
@Data
public class ArticleVo {
    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;
    //标签
    private List<TagVo> tags;
    //分类
    private List<CategoryVo> categorys;
}
