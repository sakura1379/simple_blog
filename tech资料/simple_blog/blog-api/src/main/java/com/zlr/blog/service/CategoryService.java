package com.zlr.blog.service;

import com.zlr.blog.vo.CategoryVo;
import com.zlr.blog.vo.Result;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service
 * @Description
 * @create 2022-08-06-上午12:17
 */
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);

}
