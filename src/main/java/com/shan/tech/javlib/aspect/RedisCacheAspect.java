package com.shan.tech.javlib.aspect;


import com.shan.tech.javlib.configuration.RedisCacheable;
import com.shan.tech.javlib.consts.Constants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
@Aspect
public class RedisCacheAspect {

  public static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);


  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private HashOperations<String, String, Object> hashOperations;


  @Around("@annotation(redisCacheable)")
  public Object cache(ProceedingJoinPoint proceedingJoinPoint, RedisCacheable redisCacheable) throws Throwable {
    logger.info("cache is working");
    Method targetMethod = getTargetMethod(proceedingJoinPoint);
    Class<?> modelType = targetMethod.getAnnotation(RedisCacheable.class).type();
    String hashName = modelType.getName();

    Object[] args = proceedingJoinPoint.getArgs();

    String key = generateKey(proceedingJoinPoint);

    String value = (String) hashOperations.get(hashName, key);
    Object result;

    // 判断缓存是否命中
    if (value != null) {
      // 缓存命中
      logger.info("缓存命中, value = {}", value);
      // 得到被代理方法的返回值类型
      Class<?> returnType = ((MethodSignature) proceedingJoinPoint.getSignature()).getReturnType();

      // 反序列化从缓存中拿到的json
      result = deserialize(value, returnType, modelType);

      logger.info("反序列化结果 = {}", result);
    } else {
      // 缓存未命中
      logger.info("缓存未命中");

      // 跳过缓存,到后端查询数据
      result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
      // 序列化查询结果
      String jsonStr = serialize(result);

      // 获取设置的缓存时间
      int timeout = targetMethod.getAnnotation(RedisCacheable.class).expire();
      // 如果没有设置过期时间,则无限期缓存(默认-1)
      if (timeout <= 0) {
        hashOperations.put(hashName, key, jsonStr);
      } else {
        final TimeUnit unit = TimeUnit.SECONDS;
        final long rawTimeout = TimeoutUtils.toMillis(timeout, unit);
        // 设置缓存时间
        redisTemplate.execute((RedisCallback<Object>) redisConn -> {
          // 配置文件中指定了这是一个String类型的连接
          // 所以这里向下强制转换一定是安全的
          StringRedisConnection conn = (StringRedisConnection) redisConn;
          // 判断hash名是否存在
          // 如果不存在，创建该hash并设置过期时间
          if (!conn.exists(hashName)) {
            conn.hSet(hashName, key, jsonStr);
            conn.expire(hashName, rawTimeout);
          } else {
            conn.hSet(hashName, key, jsonStr);
          }

          return null;
        });
      }

      return result;
    }

    return proceedingJoinPoint.proceed(args);
  }


  /**
   * get target method
   *
   * @param pjp ProceedingJoinPoint
   * @return target method
   */
  private Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException,
          SecurityException {
    Signature sig = pjp.getSignature();
    if (!(sig instanceof MethodSignature)) {
      throw new IllegalArgumentException("The annotation can only apply to method.");
    }
    MethodSignature msig = (MethodSignature) sig;
    Object target = pjp.getTarget();
    return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
  }

  /**
   * generate key for cache by class name method name and arguments
   *
   * @param proceedingJoinPoint ProceedingJoinPoint proceedingJoinPoint
   * @return generated key
   */
  private String generateKey(ProceedingJoinPoint proceedingJoinPoint) {

    // class name
    String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
    // method name
    String methodName = proceedingJoinPoint.getSignature().getName();
    // arguments
    Object[] args = proceedingJoinPoint.getArgs();
    StringBuilder sb = new StringBuilder(clazzName);
    sb.append(Constants.DELIMITER);
    sb.append(methodName);
    sb.append(Constants.DELIMITER);
    if (args != null) {
      for (Object arg : args) {
        sb.append(arg);
        sb.append(Constants.DELIMITER);
      }
    }

    // 去除最后一个分隔符
    sb.replace(sb.length() - 1, sb.length(), Constants.DELIMITER);
    return sb.toString();
  }

  /**
   * serialized string
   *
   * @param source objects need to be serialized
   * @return json string
   */
  private String serialize(Object source) {
    return JSON.toJSONString(source);
  }

  /**
   * deserialize
   *
   * @param source serialized object string
   * @param clazz class
   * @param modelType model type like user genre
   * @return deserialize object
   */
  private Object deserialize(String source, Class<?> clazz, Class<?> modelType) {
    // check whether  string is serialized from List
    if (clazz.isAssignableFrom(List.class)) {
      return JSON.parseArray(source, modelType);
    }

    // deserialize from json
    return JSON.parseObject(source, clazz);
  }
}
