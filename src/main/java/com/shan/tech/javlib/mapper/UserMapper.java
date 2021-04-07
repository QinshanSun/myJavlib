package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

  User findById(Long id);

  List<User> findAll();
}
