package com.seven.blog.service;

import com.seven.blog.vo.CommentVo;
import com.seven.blog.vo.params.CommentParams;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/21 21:41
 * @Description
 */
public interface CommentService {

    /*
     * 根据文章id获取该文章的评论信息
     * @param articleId
     * @date 2021/12/22 17:45
     * @return List<CommentVo>
     */
    List<CommentVo> getComments(Long articleId);

    /*
     * 评论文章
     * @param commentParams
     * @date 2021/12/22 16:34
     * @return int
     */
    int makeComment(CommentParams commentParams);
}
