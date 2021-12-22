package com.seven.blog.controller;

import com.seven.blog.service.LoginService;
import com.seven.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/17 20:32
 * @Description
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    /*
     * 用户注销
     * @param token
     * @date 2021/12/22 17:42
     * @return Result
     */
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
