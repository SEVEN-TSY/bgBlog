package com.seven.blog.service;

import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.LoginParam;

public interface LoginService {

    Result login(LoginParam loginParam);

    Result logout(String token);

    Result register(LoginParam loginParam);

    SysUser checkToken(String token);
}
