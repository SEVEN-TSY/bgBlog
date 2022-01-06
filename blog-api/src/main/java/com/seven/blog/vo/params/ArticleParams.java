package com.seven.blog.vo.params;

import com.seven.blog.dao.pojo.ArticleBody;
import com.seven.blog.dao.pojo.Category;
import com.seven.blog.dao.pojo.Tag;
import com.seven.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/28 21:08
 * @Description
 */
@Data
public class ArticleParams {

    //标题
    private String title;

    private Long id;

    //文章主体
    private ArticleBodyParams body;

    //分类
    private Category category;

    //概述
    private String summary;

    //标签
    private List<TagVo> tags;

}
