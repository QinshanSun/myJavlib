package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.mapper.UserMapper;
import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private RedisTemplate redisTemplate;
  private UserMapper userMapper;
  private Logger logger = LogManager.getLogger(this.getClass());

  @Override
  public Optional<User> findById(Long id) {
    if (redisTemplate.opsForHash().hasKey(RedisConst.HASH_ALL_USER, id)) {
      logger.info("Get User from Redis");
      return Optional.ofNullable((User) redisTemplate.opsForHash().get(RedisConst.HASH_ALL_USER, id));
    }
    return userMapper.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }

  @Autowired
  public void setRedisTemplate(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Autowired
  public void setUserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }
}
