package com.seven.blog.utils;

import com.seven.blog.dao.pojo.SysUser;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/18 18:00
 * @Description
 */
public class SysUserLocalThread {
    private SysUserLocalThread(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
