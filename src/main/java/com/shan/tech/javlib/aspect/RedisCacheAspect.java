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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import com.alibaba.fastjson.JSON;
import java.util.List;


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



  /**
   * 得到目标方法
   * @param pjp
   * @return 目标方法
   */
  private Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException,
          SecurityException {
    Signature sig = pjp.getSignature();
    if (!(sig instanceof MethodSignature)) {
      throw new IllegalArgumentException("该注解只能用于方法");
    }
    MethodSignature msig = (MethodSignature) sig;
    Object target = pjp.getTarget();
    return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
  }

  /**
   * 通过类名，方法名和参数来获取对应的key
   * @param pjp
   * @return 生成的key
   */
  private String getKey(ProceedingJoinPoint pjp) {
    // 获取类名
    String clazzName = pjp.getTarget().getClass().getName();
    // 获取方法名
    String methodName = pjp.getSignature().getName();
    // 方法参数
    Object[] args = pjp.getArgs();
    // 生成key
    return generateKey(clazzName, methodName, args);
  }

  /**
   * 生成缓存需要的key
   * @param clazzName
   * @return 生成的key
   */
  private String generateKey(String clazzName, String methodName, Object[] args) {
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
   * 序列化数据
   * @param source
   * @return json字符串
   */
  private String serialize(Object source) {
    return JSON.toJSONString(source);
  }

  /**
   * 反序列化
   * @param source
   * @param clazz
   * @param modelType
   * @return 反序列化的数据
   */
  private Object deserialize(String source, Class<?> clazz, Class<?> modelType) {
    // 判断是否为List
    if (clazz.isAssignableFrom(List.class)) {
      return JSON.parseArray(source, modelType);
    }

    // 正常反序列化
    return JSON.parseObject(source, clazz);
  }
}
