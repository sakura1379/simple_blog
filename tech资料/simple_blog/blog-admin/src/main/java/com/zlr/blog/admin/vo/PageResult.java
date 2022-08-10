package com.zlr.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.vo
 * @Description
 * @create 2022-08-09-下午10:26
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}
