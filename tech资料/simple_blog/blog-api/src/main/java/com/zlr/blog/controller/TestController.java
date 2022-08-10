package com.zlr.blog.controller;

import com.zlr.blog.dao.pojo.SysUser;
import com.zlr.blog.utils.UserThreadLocal;
import com.zlr.blog.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.controller
 * @Description
 * @create 2022-08-05-下午11:56
 */
@RestController
@RequestMapping("test")
@Api(tags = "测试", value = "/test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
