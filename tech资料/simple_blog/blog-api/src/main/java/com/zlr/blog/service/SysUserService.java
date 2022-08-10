package com.zlr.blog.service;

import com.zlr.blog.dao.pojo.SysUser;
import com.zlr.blog.vo.Result;
import com.zlr.blog.vo.UserVo;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.service
 * @Description
 * @create 2022-08-02-下午2:30
 */
public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long userId);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
