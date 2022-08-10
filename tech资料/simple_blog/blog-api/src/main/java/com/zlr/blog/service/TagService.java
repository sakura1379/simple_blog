package com.zlr.blog.service;

import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.TagVo;

import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service
 * @Description
 * @create 2022-08-02-下午7:33
 */
public interface TagService {

    List<TagVo> findTagsByArticleId(Long id);

    Result hots(int limit);

    /**
     * 查询所有的文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
