package com.seven.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/19 15:20
 * @Description
 */
@Data
public class CategoryVo {

    private String id;

    private String avatar;

    private String categoryName;

    private String description;
}
