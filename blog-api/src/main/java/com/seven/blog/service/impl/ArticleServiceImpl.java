package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.blog.dao.dos.Archives;
import com.seven.blog.dao.mapper.ArticleMapper;
import com.seven.blog.dao.pojo.Article;
import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.service.*;
import com.seven.blog.vo.ArticleVo;
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
    private ArticleBodyService articleBodyService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //置顶排序
        //按照时间逆序排序
        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, lambdaQueryWrapper);
        List<Article> articleList = articlePage.getRecords();
        List<ArticleVo> articleVoList=copyList(articleList,true,true);
        return articleVoList;
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
