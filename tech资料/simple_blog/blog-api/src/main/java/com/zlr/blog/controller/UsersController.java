package com.zlr.blog.controller;

import com.zlr.blog.service.SysUserService;
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
 * @create 2022-08-05-下午8:18
 */
@RestController
@RequestMapping("users")
@Api(tags = "用户", value = "/users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    ///users/currentUser
    @GetMapping("currentUser")
    @ApiOperation(value = "获取当前用户信息", notes = "在header中放入token")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
