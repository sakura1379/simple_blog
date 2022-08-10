package com.zlr.blog.dao.pojo;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.dao.pojo
 * @Description
 * @create 2022-08-03-下午12:24
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;

}
