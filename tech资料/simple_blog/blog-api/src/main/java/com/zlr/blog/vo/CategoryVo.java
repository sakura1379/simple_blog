package com.zlr.blog.vo;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.vo
 * @Description
 * @create 2022-08-03-下午12:49
 */
@Data
public class CategoryVo {

    private String id;

    private String avatar;

    private String categoryName;

    private String description;
}