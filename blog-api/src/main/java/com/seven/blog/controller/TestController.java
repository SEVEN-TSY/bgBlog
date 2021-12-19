package com.seven.blog.controller;

import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.utils.SysUserLocalThread;
import com.seven.blog.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/18 17:52
 * @Description 测试登录拦截器
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = SysUserLocalThread.get();
        return Result.success(sysUser);
    }
}
