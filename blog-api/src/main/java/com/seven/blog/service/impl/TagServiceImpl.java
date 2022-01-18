package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.blog.dao.mapper.TagMapper;
import com.seven.blog.dao.pojo.Tag;
import com.seven.blog.service.TagService;
import com.seven.blog.vo.TagVo;
import javafx.scene.control.Label;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/8 19:54
 * @Description
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tagList=tagMapper.findTagsByArticleId(id);
        return copyList(tagList);
    }

    @Override
    public List<TagVo> getHotTag(int limit) {
        List<Long> getHotTagIds=tagMapper.getHotTagIds(limit);
        if(CollectionUtils.isEmpty(getHotTagIds)){
            return Collections.emptyList();
        }
        List<Tag> tagList= tagMapper.findtagsByTagIds(getHotTagIds);
        return copyList(tagList);
    }

    @Override
    public List<TagVo> getAllTags() {
        LambdaQueryWrapper<Tag> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(lambdaQueryWrapper);
        return copyList(tagList);
    }

    @Override
    public List<TagVo> getAllTagsDetail() {
        LambdaQueryWrapper<Tag> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        List<Tag> tagList = tagMapper.selectList(lambdaQueryWrapper);
        return copyList(tagList);
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList=new ArrayList<>();
        for (int i = 0,l=tagList.size(); i < l; i++) {
            tagVoList.add(copy(tagList.get(i)));
        }
        return tagVoList;

    }
    private TagVo copy(Tag tag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

}
