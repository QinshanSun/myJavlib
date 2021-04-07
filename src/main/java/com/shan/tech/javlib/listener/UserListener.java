package com.shan.tech.javlib.listener;

import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class UserListener implements ServletContextListener {

  @Resource
  private RedisTemplate redisTemplate;

  @Resource
  private UserService userService;

  private static final String ALL_USER = "ALL_USER_LIST";

  Logger logger = LogManager.getLogger(this.getClass());

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    logger.info("redis start ");
    List<User> userList = userService.findAll();
    redisTemplate.delete(ALL_USER);

    redisTemplate.opsForList().leftPushAll(ALL_USER, userList);

    List<User> redisUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
    logger.info("redis user: " + redisUserList.size());
  }

}
