package com.seven.blog.dao.pojo;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/19 15:50
 * @Description 文章类型
 */
@Data
public class Category {
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
