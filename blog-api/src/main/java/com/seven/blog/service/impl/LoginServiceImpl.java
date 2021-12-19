package com.seven.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.service.LoginService;
import com.seven.blog.service.SysUserService;
import com.seven.blog.utils.JWTUtils;
import com.seven.blog.vo.ErrorCode;
import com.seven.blog.vo.Result;
import com.seven.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/15 11:04
 * @Description
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    //加密盐
    private final static String slat="mszlu!@#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        //判参数
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //加密
        String pwd= DigestUtils.md5Hex(password+slat);
        //查数据库，账号+加密密码
        SysUser sysUser= sysUserService.findUser(account,pwd);
        //判断查询结果
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功时，使用JWT生成token
        String token = JWTUtils.createToken(sysUser.getId());
        //将token放入redis缓存 一天
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        //返回token
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /*
         1.验证传入参数是否为空
         2.验证输入账户是否已经存在
         3.如果存在，返回失败信息
         4.如果不存在，创建用户，生成token，放入redis，返回token注意添加事务
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //验证账户是否存在
        SysUser user= sysUserService.findUserByAccount(account);
        if(!(user==null)){
            return Result.fail(ErrorCode.ACCOUNT_ALREADY_EXIST.getCode(),ErrorCode.ACCOUNT_ALREADY_EXIST.getMsg());
        }
        SysUser sysUser=new SysUser();
        sysUser.setAccount(account);
        sysUser.setNickname(nickname);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        //验证token是否为空
        if(map==null){
            return null;
        }
        String loginUserJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        //验证redis是否存在
        if(StringUtils.isBlank(loginUserJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(loginUserJson, SysUser.class);

        return sysUser;
    }

    //public static void main(String[] args) {
    //    System.out.println(DigestUtils.md5Hex("admin" + slat));
    //}
}
