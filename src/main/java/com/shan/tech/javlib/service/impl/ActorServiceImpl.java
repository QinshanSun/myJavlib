package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.mapper.ActorMapper;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorMapper actorMapper;

  @Override
  public Optional<Actor> findById(Long id) {
    return actorMapper.findById(id);
  }

  @Override
  public List<Actor> findAll() {
    return actorMapper.findAll();
  }

  @Override
  public List<Actor> findActorsByName(String name) {
    return actorMapper.findActorsByName(name);
  }

  @Autowired
  public void setActorMapper(ActorMapper actorMapper) {
    this.actorMapper = actorMapper;
  }
}
