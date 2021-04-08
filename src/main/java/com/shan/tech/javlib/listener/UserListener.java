package com.shan.tech.javlib.listener;

import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@Component
public class UserListener implements ServletContextListener {

  private RedisTemplate redisTemplate;

  private UserService userService;

  private static final String ALL_USER = "ALL_USER_LIST";

  Logger logger = LogManager.getLogger(this.getClass());

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    logger.info("redis start ");
    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
    userService = webApplicationContext.getBean(UserService.class);
    redisTemplate = webApplicationContext.getBean("redisTemplate",RedisTemplate.class);
    List<User> userList = userService.findAll();
    redisTemplate.delete(ALL_USER);

    redisTemplate.opsForList().leftPushAll(ALL_USER, userList);

    List<User> redisUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
    logger.info("redis user: " + redisUserList.size());
  }

}
