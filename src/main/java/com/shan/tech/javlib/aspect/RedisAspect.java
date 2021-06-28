package com.shan.tech.javlib.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;

public interface RedisAspect {

  /**
   * get target method
   *
   * @param pjp ProceedingJoinPoint
   * @return target method
   */
  default Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
    Signature sig = pjp.getSignature();
    if (!(sig instanceof MethodSignature)) {
      throw new IllegalArgumentException("The annotation can only apply to method.");
    }
    MethodSignature msig = (MethodSignature) sig;
    Object target = pjp.getTarget();
    return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
  }

  /**
   * serialized string
   *
   * @param source objects need to be serialized
   * @return json string
   */
  default String serialize(Object source) {
    return JSON.toJSONString(source);
  }


  /**
   * deserialize
   *
   * @param source    serialized object string
   * @param clazz     class
   * @param modelType model type like user genre
   * @return deserialize object
   */
  default Object deserialize(String source, Class<?> clazz, Class<?> modelType) {
    // check whether string is serialized from List
    if (clazz.isAssignableFrom(List.class)) {
      return JSON.parseArray(source, modelType);
    }

    // deserialize from json
    return JSON.parseObject(source, clazz);
  }
}
