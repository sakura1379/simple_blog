package com.zlr.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.vo
 * @Description
 * @create 2022-08-09-下午9:49
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;


    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code, String msg){
        return new Result(false,code,msg,null);
    }
}
