package com.zlr.blog.service;

import com.zlr.blog.dao.pojo.SysUser;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.params.LoginParam;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service
 * @Description
 * @create 2022-08-05-下午12:31
 */
public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
