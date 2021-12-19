package com.seven.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/3 22:04
 * @Description 用户
 */
@Data
public class SysUser {

    @TableId
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
