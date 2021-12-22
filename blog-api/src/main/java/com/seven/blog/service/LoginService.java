package com.seven.blog.service;

import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.LoginParam;

public interface LoginService {

    /*
     * 登录
     * @param loginParam
     * @date 2021/12/22 17:45
     * @return Result
     */
    Result login(LoginParam loginParam);

    /*
     * 注销
     * @param token
     * @date 2021/12/22 17:45
     * @return Result
     */
    Result logout(String token);

    /*
     * 注册
     * @param loginParam
     * @date 2021/12/22 17:46
     * @return Result
     */
    Result register(LoginParam loginParam);

    /*
     * 检查token
     * @param token
     * @date 2021/12/22 17:46
     * @return SysUser
     */
    SysUser checkToken(String token);
}
