package com.seven.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.blog.dao.mapper.SysUserMapper;
import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.service.LoginService;
import com.seven.blog.service.SysUserService;
import com.seven.blog.vo.LoginUserVo;
import com.seven.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/4 11:50
 * @Description
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SysUser selectById(Long id) {
        SysUser sysUser =sysUserMapper.selectById(id);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("sevenxy");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String pwd) {
        LambdaQueryWrapper<SysUser> sysUserQueryWrapper = new LambdaQueryWrapper<>();
        sysUserQueryWrapper.eq(SysUser::getAccount,account);
        sysUserQueryWrapper.eq(SysUser::getPassword,pwd);
        sysUserQueryWrapper.select(SysUser::getId,SysUser::getNickname,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        sysUserQueryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(sysUserQueryWrapper);
        return sysUser;
    }

    @Override
    public LoginUserVo getUserInfoByToken(String token) {
        SysUser sysUser = loginService.checkToken(token);
        //将sysUser转换为loginUserVO
        LoginUserVo loginUserVO = new LoginUserVo();
        loginUserVO.setAccount(sysUser.getAccount());
        loginUserVO.setNickname(sysUser.getNickname());
        loginUserVO.setAvatar(sysUser.getAvatar());
        loginUserVO.setId(sysUser.getId());
        return loginUserVO;
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public Result save(SysUser sysUser) {
        return Result.success(sysUserMapper.insert(sysUser));
    }
}
