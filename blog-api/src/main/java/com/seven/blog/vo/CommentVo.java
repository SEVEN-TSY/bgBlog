package com.seven.blog.vo;

import com.seven.blog.dao.pojo.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/21 21:41
 * @Description
 */
@Data
public class CommentVo {
    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;

}
