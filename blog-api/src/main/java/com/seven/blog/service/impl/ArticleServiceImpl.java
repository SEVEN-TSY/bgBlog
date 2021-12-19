package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.blog.dao.dos.Archives;
import com.seven.blog.dao.mapper.ArticleMapper;
import com.seven.blog.dao.pojo.Article;
import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.service.ArticleService;
import com.seven.blog.service.SysUserService;
import com.seven.blog.service.TagService;
import com.seven.blog.vo.ArticleVo;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.TagVo;
import com.seven.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 10:14
 * @Description
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticlesPage(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //置顶排序
        //按照时间逆序排序
        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, lambdaQueryWrapper);
        List<Article> articleList = articlePage.getRecords();
        List<ArticleVo> articleVoList=copyList(articleList,true,true);
        return Result.success(articleVoList);
    }

    @Override
    public Result listHotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listNewArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " +limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archives=articleMapper.listArchives();
        return Result.success(archives);
    }

    @Override
    public Result findArticleById(Long id) {
        return null;
    }

    private List<ArticleVo> copyList(List<Article> articleList, Boolean isTag, Boolean isAuthor) {
        List<ArticleVo> articleVoList =new ArrayList<>();
        for (Article article : articleList) {
            articleVoList.add(copy(article,isTag,isAuthor));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, Boolean isTag, Boolean isAuthor){
        ArticleVo articleVO=new ArticleVo();
        //将相同属性copy
        BeanUtils.copyProperties(article,articleVO);
        articleVO.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //标签vo
        if(isTag){
            List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(article.getId());
            articleVO.setTags(tagsByArticleId);
        }
        //作者
        if(isAuthor){
             SysUser author=sysUserService.selectById(article.getAuthorId());
             articleVO.setAuthor(author.getNickname());
        }
        return articleVO;
    }
}
