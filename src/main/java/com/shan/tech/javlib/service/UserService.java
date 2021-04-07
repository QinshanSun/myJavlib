package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.User;

import java.util.List;


public interface UserService {

  User findById(Long id);

  List<User> findAll();


}
