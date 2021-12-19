package com.seven.blog.controller;

import com.seven.blog.service.LoginService;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/13 22:31
 * @Description 登录接口
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
