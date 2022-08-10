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
 * @create 2022-08-05-下午7:53
 */
@RestController
@RequestMapping("register")
@Api(tags = "注册", value = "/register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @LogAnnotation(module="注册",operator="注册")
    @ApiOperation(value = "注册",notes = "@RequestBody LoginParam loginParam（account和password）")
    public Result register(@RequestBody LoginParam loginParam){
        //sso 单点登录，后期如果把登录注册功能 提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}