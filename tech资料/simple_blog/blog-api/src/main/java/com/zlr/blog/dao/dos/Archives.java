package com.zlr.blog.dao.dos;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.dao.dos
 * @Description
 * @create 2022-08-03-下午10:56
 */
@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}
