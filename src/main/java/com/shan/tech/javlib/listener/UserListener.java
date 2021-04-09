package com.shan.tech.javlib.listener;

import com.shan.tech.javlib.consts.RedisConsts;
import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserListener implements ServletContextListener {

  private RedisTemplate redisTemplate;

  private UserService userService;

  Logger logger = LogManager.getLogger(this.getClass());

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    logger.info("redis start ");
    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
    userService = webApplicationContext.getBean(UserService.class);
    redisTemplate = webApplicationContext.getBean("redisTemplate", RedisTemplate.class);
    List<User> userList = userService.findAll();
    redisTemplate.delete(RedisConsts.HASH_ALL_USER);
    redisTemplate.opsForHash().putAll(RedisConsts.HASH_ALL_USER, userList.stream().collect(Collectors.toMap(User::getId, User -> User)));
  }

}
