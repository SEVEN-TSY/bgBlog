package com.seven.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 9:43
 * @Description
 */
@Data
@AllArgsConstructor
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
