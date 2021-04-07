package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.pojo.User;
import com.shan.tech.javlib.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public User getUserById(@RequestParam(name = "Id") Long Id) {
    return userService.findById(Id);
  }


  @GetMapping(value = "/all")
  public List<User> findAllUsers() {
    return userService.findAll();
  }
}
