package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

  Optional<User> findById(Long id);

  List<User> findAll();


}
