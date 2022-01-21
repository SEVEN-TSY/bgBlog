package com.seven.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

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
    //private List<CategoryVo> categorys;
    private List<CategoryVo> categorys;
}
