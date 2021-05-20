package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.mapper.ActorMapper;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorMapper actorMapper;

  private HashOperations<String, String, Object> hashOperations;

  @Override
  public Optional<Actor> findById(Long id) {
    return actorMapper.findById(id);
  }

  @Override
  public Optional<Actor> findByLabel(String label) {
    return actorMapper.findByLabel(label);
  }

  @Override
  public List<Actor> findAll() {
    return actorMapper.findAll();
  }

  @Override
  public List<Actor> findActorsByName(String name) {
    return actorMapper.findActorsByName(name);
  }

  @Override
  public int insertActor(Actor actor) {
    return actorMapper.insertActor(actor);
  }

  @Autowired
  public void setActorMapper(ActorMapper actorMapper) {
    this.actorMapper = actorMapper;
  }

  @Autowired
  public void setHashOperations(HashOperations<String, String, Object> hashOperations) {
    this.hashOperations = hashOperations;
  }
}
