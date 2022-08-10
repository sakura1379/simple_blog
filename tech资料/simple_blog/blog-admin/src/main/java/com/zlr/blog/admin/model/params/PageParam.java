package com.zlr.blog.admin.model.params;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.model.params
 * @Description
 * @create 2022-08-09-下午10:26
 */
@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
