package com.seven.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.seven.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author lixinyi03
 * @version 1.0
 * @date 2022/1/21 15:22
 * @Description 统一缓存切面
 */
@Aspect
@Slf4j
@Component
public class CacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.seven.blog.common.cache.Cache)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        try {
            Signature signature=proceedingJoinPoint.getSignature();
            //类名
            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();

            //获取被切方法
            //参数类型
            Class[] prarmTypes = new Class[proceedingJoinPoint.getArgs().length];
            //参数
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuilder paramsBuilder=new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if(args[i]!=null){
                    paramsBuilder.append(JSON.toJSONString(args[i]));
                    prarmTypes[i]=args[i].getClass();
                }else {
                    prarmTypes[i]=null;
                }
            }
            String params=paramsBuilder.toString();
            if(StringUtils.isNotEmpty(params)){
                params= DigestUtils.md5Hex(params);
            }

            Method method = proceedingJoinPoint.getTarget().getClass().getMethod(methodName, prarmTypes);
            //根据方法获取缓存注解信息
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            String redisKey=name+"::"+className+"::"+methodName+"::"+params;
            String redisValue= (String) redisTemplate.opsForValue().get(redisKey);
            if(StringUtils.isNotEmpty(redisValue)){
                log.info("~~~get data from Redis cache,{},{}",className,methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            //为空，执行目标方法
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("~~~add into Redis cache,{},{}",className,methodName);
            return proceed;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999,"系统错误");


    }
}
