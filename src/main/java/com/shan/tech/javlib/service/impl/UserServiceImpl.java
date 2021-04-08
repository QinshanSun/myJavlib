package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.mapper.UserMapper;
import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private UserMapper userMapper;

  @Override
  public Optional<User> findById(Long id) {
    return userMapper.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }

  @Autowired
  public void setUserMapper(UserMapper userMapper){
    this.userMapper = userMapper;
  }
}
