package com.seven.blog.service;

import com.seven.blog.vo.CommentVo;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/21 21:41
 * @Description
 */
public interface CommentService {
    public List<CommentVo> getComments(Long articleId);
}
