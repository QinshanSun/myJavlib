package com.shan.tech.javlib.configuration;

import com.shan.tech.javlib.engine.ScheduledCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@Configuration
public class ScheduledConfig {

  @Conditional(ScheduledCondition.class)
  @Bean
  public ScheduledAnnotationBeanPostProcessor processor() {
    return new ScheduledAnnotationBeanPostProcessor();
  }
}
