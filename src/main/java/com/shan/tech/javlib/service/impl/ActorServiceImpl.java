package com.shan.tech.javlib.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shan.tech.javlib.consts.RedisConst;
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
    if (hashOperations.hasKey(RedisConst.HASH_ALL_ACTOR, id)) {
      Actor actor = (Actor) hashOperations.get(RedisConst.HASH_ALL_ACTOR, id);
      return Optional.ofNullable(actor);
    }
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
  public PageInfo<Actor> findActorsByPage(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<Actor> actorPage = actorMapper.findByPage();
    return new PageInfo<>(actorPage);
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
