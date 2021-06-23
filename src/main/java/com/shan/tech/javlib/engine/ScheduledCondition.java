package com.shan.tech.javlib.engine;

import com.shan.tech.javlib.consts.RedisConst;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class ScheduledCondition implements Condition {
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    /*
    ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) context.getBeanFactory().getBean("valueOperations");
    return Boolean.parseBoolean(valueOperations.get(RedisConst.ENABLE_SCHEDULED_TASK));
    * */
    return true;
  }
}
