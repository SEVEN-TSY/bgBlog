package com.seven.blog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/22 16:28
 * @Description
 */
@Data
public class CommentParams {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parent;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUserId;
}
