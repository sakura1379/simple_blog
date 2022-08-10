package com.zlr.blog.dao.pojo;

import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.dao.pojo
 * @Description
 * @create 2022-08-02-下午12:26
 */
@Data
public class SysUser {
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;

}
