package com.zlr.blog.controller;

import com.zlr.blog.common.aop.LogAnnotation;
import com.zlr.blog.service.LoginService;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.params.LoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.controller
 * @Description
 * @create 2022-08-03-下午11:25
 */
@RestController
@RequestMapping("login")
@Api(tags = "登录", value = "/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    @LogAnnotation(module="登录",operator="登录")
    @ApiOperation(value = "登录", notes = "@RequestBody LoginParam loginParam（account和password）")
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户  访问用户表
        return loginService.login(loginParam);
    }
}
