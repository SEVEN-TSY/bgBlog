package com.seven.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.blog.dao.pojo.Tag;

import java.util.List;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/8 19:55
 * @Description
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long id);

    List<Long> getHotTagIds(int limit);

    List<Tag> findtagsByTagIds(List<Long> tagIds);
}
