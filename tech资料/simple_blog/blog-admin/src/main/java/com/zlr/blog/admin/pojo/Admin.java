package com.zlr.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.pojo
 * @Description
 * @create 2022-08-09-下午9:39
 */
@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}
