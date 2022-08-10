package com.zlr.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.vo
 * @Description
 * @create 2022-08-02-下午12:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;

    private Integer code;

    private String msg;

    private Object data;


    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }
}
