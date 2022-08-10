package com.zlr.blog.utils;

import com.zlr.blog.dao.pojo.SysUser;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.utils
 * @Description
 * @create 2022-08-05-下午11:50
 */
public class UserThreadLocal {

    private UserThreadLocal(){}
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
