package com.zlr.blog.vo.params;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.vo.params
 * @Description
 * @create 2022-08-06-上午1:03
 */
@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
