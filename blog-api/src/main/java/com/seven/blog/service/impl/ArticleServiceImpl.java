package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.blog.common.aop.LogAnnotation;
import com.seven.blog.dao.dos.Archives;
import com.seven.blog.dao.mapper.ArticleMapper;
import com.seven.blog.dao.pojo.Article;
import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.service.*;
import com.seven.blog.utils.SysUserLocalThread;
import com.seven.blog.vo.ArticleVo;
import com.seven.blog.vo.TagVo;
import com.seven.blog.vo.params.ArticleParams;
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
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage= articleMapper.listArticles(
                page,
                pageParams.getMonth(),
                pageParams.getYear(),
                pageParams.getCategoryId(),
                pageParams.getTagId());
        List<Article> records = articleIPage.getRecords();
        return copyList(records,true,true);


        //LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        ////置顶排序
        ////按照时间逆序排序
        //lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        //Page<Article> articlePage = articleMapper.selectPage(page, lambdaQueryWrapper);
        //List<Article> articleList = articlePage.getRecords();
        //List<ArticleVo> articleVoList=copyList(articleList,true,true);
        //return articleVoList;
    }

    @Override
    public List<ArticleVo> listHotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return copyList(articles,false,false);
    }

    @Override
    public List<ArticleVo> listNewArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " +limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return copyList(articles,false,false);
    }

    @Override
    public List<Archives> listArchives() {
        List<Archives> archives=articleMapper.listArchives();
        return archives;
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public ArticleVo findArticleById(Long id) {
        /*
        1.根据文章id查询出来文章信息
        2.将Article对象copy为ArticleVo对象
            2.1复制基本信息，copy标签、作者
            2.2根据存储的文章内容id获取文章主体内容
            2.3根据存储的分类id获取类别内容
         */
        Article article = articleMapper.selectById(id);
        /*
        3.更新阅读次数
         */
        threadService.updateViewCounts(articleMapper,article);

        return copy(article,true,true,true,true);
    }

    @Override
    public Long publishArticle(ArticleParams articleParams) {
        /*
        主要工作：拼Article对象
        步骤：
        1.获取当前登录用户
        2.添加title、summary、category等相关信息，并预设bodyId
        3.添加文章标签关系
        4.添加文章body信息
        5.添加文章和body的关系信息
         */
        Article article = new Article();
        //作者id--当前登录用户
        SysUser sysUser = SysUserLocalThread.get();
        article.setAuthorId(sysUser.getId());
        article.setViewCounts(0);
        article.setBodyId(-1L);
        article.setCommentCounts(0);
        article.setCategoryId(articleParams.getCategory().getId());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());
        article.setWeight(Article.Article_Common);
        article.setCreateDate(System.currentTimeMillis());

        //添加后，生成的id会自动set到article
        articleMapper.insert(article);

        //添加文章-标签关系表
        articleTagService.insertArticleTag(article.getId(),articleParams.getTags());

        //添加文章body信息
        Long bodyId=articleBodyService.insertBody(article.getId(),articleParams.getBody());
        article.setBodyId(bodyId);
        articleMapper.updateById(article);
        return article.getId();

    }

    private List<ArticleVo> copyList(List<Article> articleList, Boolean isTag, Boolean isAuthor) {
        List<ArticleVo> articleVoList =new ArrayList<>();
        for (Article article : articleList) {
            articleVoList.add(copy(article,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }
    //多态
    private List<ArticleVo> copyList(List<Article> articleList, Boolean isTag, Boolean isAuthor,Boolean isBody,Boolean isCategory) {
        List<ArticleVo> articleVoList =new ArrayList<>();
        for (Article article : articleList) {
            articleVoList.add(copy(article,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, Boolean isTag, Boolean isAuthor,Boolean isBody,Boolean isCategory){
        ArticleVo articleVO=new ArticleVo();
        //将相同属性copy
        BeanUtils.copyProperties(article,articleVO);
        articleVO.setId(String.valueOf(article.getId()));
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
        //主体
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVO.setBody(articleBodyService.getBodyVoById(bodyId));
        }
        //分类
        if(isCategory){
            Long categoryId = article.getCategoryId();
            articleVO.setCategorys(categoryService.getCategoryVoById(categoryId));
        }
        return articleVO;
    }
}
