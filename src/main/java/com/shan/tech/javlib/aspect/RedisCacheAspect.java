package com.shan.tech.javlib.aspect;


import com.shan.tech.javlib.configuration.RedisCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class RedisCacheAspect {

  public static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);


  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Around("@annotation(redisCacheable)")
  public Object cache(ProceedingJoinPoint jp, RedisCacheable redisCacheable) throws Throwable {
    logger.info("cache is working");
         //Get the class name, method name and parameters
    String clazzName = jp.getTarget().getClass().getName();
    String methodName = jp.getSignature().getName();
    Object[] args = jp.getArgs();
    return jp.proceed(args);
  }
}
