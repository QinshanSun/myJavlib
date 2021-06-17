package com.shan.tech.javlib.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shan.tech.javlib.consts.Constants;
import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.mapper.ActorMapper;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.service.ActorService;
import com.shan.tech.javlib.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorMapper actorMapper;

  private HashOperations<String, String, Object> hashOperations;

  private ListOperations<String, String> listOperations;

  private ValueOperations<String, String> valueOperations;

  @Override
  public Optional<Actor> findById(Long id) {
    if (hashOperations.hasKey(RedisConst.HASH_ALL_ACTOR, id)) {
      Actor actor = (Actor) hashOperations.get(RedisConst.HASH_ALL_ACTOR, id);
      return Optional.ofNullable(actor);
    }
    return actorMapper.findById(id);
  }

  @Override
  public List<Actor> findByLabel(String label) {
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
    int res = actorMapper.insertActor(actor);
    // put the actor url for scrapy spider to use
    if (res > 0){
      String URL = RedisUtils.getDomain(valueOperations) + Constants.SLASH + actor.getLabel();
      RedisUtils.pushSpiderStartURL(listOperations, RedisConst.VIDEO_SPIDER, URL);
    }
    return res;
  }

  @Override
  public int updateActor(Actor actor) {
    return actorMapper.updateActor(actor);
  }

  @Autowired
  public void setActorMapper(ActorMapper actorMapper) {
    this.actorMapper = actorMapper;
  }

  @Autowired
  public void setHashOperations(HashOperations<String, String, Object> hashOperations) {
    this.hashOperations = hashOperations;
  }

  @Autowired
  public void setListOperations(ListOperations<String, String> listOperations){
    this.listOperations = listOperations;
  }

  @Autowired
  public void setValueOperations(ValueOperations<String, String> valueOperations) {
    this.valueOperations = valueOperations;
  }
}
