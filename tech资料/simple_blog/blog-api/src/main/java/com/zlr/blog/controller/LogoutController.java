package com.zlr.blog.controller;

import com.zlr.blog.service.LoginService;
import com.zlr.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.controller
 * @Description
 * @create 2022-08-05-下午2:21
 */
@RestController
@RequestMapping("logout")
@Api(tags = "登出", value = "/logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    @ApiOperation(value = "登出")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
