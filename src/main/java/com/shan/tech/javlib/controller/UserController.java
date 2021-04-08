package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  private RedisTemplate redisTemplate;

  Logger logger = LogManager.getLogger(this.getClass());

  @GetMapping
  public User getUserById(@RequestParam(name = "Id") Long Id) {
    if (redisTemplate.opsForHash().hasKey("ALL_USER_LIST", Id)) {
      logger.info("Get User from Redis");
      return (User) redisTemplate.opsForHash().get("ALL_USER_LIST", Id);
    }
    return userService.findById(Id).orElseThrow();
  }


  @GetMapping(value = "/all")
  public List<User> findAllUsers() {
    return userService.findAll();
  }


  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setRedisTemplate(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }
}
