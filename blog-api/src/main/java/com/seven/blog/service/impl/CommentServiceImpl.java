package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.blog.dao.mapper.CommentMapper;
import com.seven.blog.dao.pojo.Comment;
import com.seven.blog.service.CommentService;
import com.seven.blog.service.SysUserService;
import com.seven.blog.vo.CommentVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/21 21:48
 * @Description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<CommentVo> getComments(Long articleId) {
        /*
        1.根据文章id获取level=1的所有评论
        2.查询每个level=1的评论的子评论，parentId,level=2
        3.循环将子评论放入level=1评论childrens域
         */
        LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId,articleId);
        lambdaQueryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        List<CommentVo> commentVoList=copyList(comments);

        return commentVoList;
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList=new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        /*
        1.将相同属性直接复制
        2.根据某些id字段，获取对应的vo
         */
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //获取当前评论人的信息
        commentVo.setAuthor(sysUserService.getUserVoById(comment.getAuthorId()));
        //level=1 没有toUser的人的信息，level是2才有
        if(comment.getLevel()>1){
            commentVo.setToUser(sysUserService.getUserVoById(comment.getToUid()));
        }
        //根据parentId获取子评论
        List<CommentVo> childrenComments= findChildrenCommentsByParentId(comment.getId());
        commentVo.setChildrens(childrenComments);
        return commentVo;
    }
    //根据父评论获取子评论
    private List<CommentVo> findChildrenCommentsByParentId(Long id) {

        LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getParentId,id);
        lambdaQueryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        List<CommentVo> commentVoList = this.copyList(comments);
        return commentVoList;
    }

}
