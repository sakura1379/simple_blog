package com.zlr.blog.vo.params;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.vo.params
 * @Description
 * @create 2022-08-05-下午12:00
 */
@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
