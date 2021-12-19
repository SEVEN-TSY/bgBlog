package com.seven.blog.handler;

import com.seven.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/13 19:45
 * @Description 统一异常处理
 */
@ControllerAdvice//接口切面切入
public class AllExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
