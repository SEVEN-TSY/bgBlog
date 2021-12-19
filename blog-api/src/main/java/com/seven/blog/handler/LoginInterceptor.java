package com.seven.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.seven.blog.dao.pojo.SysUser;
import com.seven.blog.service.LoginService;
import com.seven.blog.service.SysUserService;
import com.seven.blog.utils.SysUserLocalThread;
import com.seven.blog.vo.ErrorCode;
import com.seven.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2021/12/18 16:49
 * @Description
 */
@Configuration
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        1.判断请求的接口路径是否为HandlerMethod（即Controller的方法）
        2.handler可能是RequestResourceHandler 访问静态资源的，不拦截
        3.判断token是否为空，为空则拦截
        4.token不为空，验证登录信息
        5.验证成功，则放行
        6.根据token获得登录用户信息，将其放入LocalThread，以使在方法里可以直接获取登录用户信息
         */
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if(StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser==null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        //将登录用户对象放入LocalThread
        SysUserLocalThread.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在接口完毕后清除LocalThread，防止内存泄漏，key为弱引用，防止对应的value一直存储在内存占用空间
        SysUserLocalThread.remove();
    }
}

