package com.seven.blog.vo.params;

import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/13 22:33
 * @Description
 */
@Data
public class LoginParam {

    private String account;

    private String password;

    //注册用
    private String nickname;
}
