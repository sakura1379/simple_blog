package com.zlr.blog.vo;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.vo
 * @Description
 * @create 2022-08-03-下午1:04
 */
@Data
public class LoginUserVo {

    private String id;

    private String account;

    private String nickname;

    private String avatar;
}
