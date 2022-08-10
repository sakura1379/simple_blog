package com.zlr.blog.dao.pojo;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.dao.pojo
 * @Description
 * @create 2022-08-03-下午12:29
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
