package com.seven.blog.controller;

import com.seven.blog.service.LoginService;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/18 15:53
 * @Description
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    /*
     * 用户注册
     * @param loginParam
     * @date 2021/12/22 17:43
     * @return Result
     */
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);

    }

}
