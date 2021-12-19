package com.seven.blog.dao.pojo;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/3 22:12
 * @Description 文章标签
 */
@Data
public class Tag {
    private Long id;

    private String avatar;

    private String tagName;
}
