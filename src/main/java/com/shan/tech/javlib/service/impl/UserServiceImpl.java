package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.mapper.UserMapper;
import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public User findById(Long id) {
    return userMapper.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }
}
