package com.seven.blog.controller;

import com.seven.blog.service.SysUserService;
import com.seven.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 11:51
 * @Description
 */
@RestController
@RequestMapping("/users")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization")String token){
        return sysUserService.getUserInfoByToken(token);
    }
}
