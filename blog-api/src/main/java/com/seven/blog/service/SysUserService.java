package com.seven.blog.service;

import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.vo.LoginUserVo;
import com.seven.blog.vo.Result;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService {

    //根据id查找user
    SysUser selectById(Long id);
    //用户登录验证
    SysUser findUser(String account, String pwd);
    //根据token获得登录用户信息
    LoginUserVo getUserInfoByToken(String token);

    SysUser findUserByAccount(String account);

    Result save(SysUser sysUser);
}
