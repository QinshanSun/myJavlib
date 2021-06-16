package com.shan.tech.javlib.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shan.tech.javlib.pojo.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {
  Optional<Actor> findById(Long id);

  Optional<Actor> findByLabel(String label);

  List<Actor> findAll();

  PageInfo<Actor> findActorsByPage(int pageNum, int pageSize);

  List<Actor> findActorsByName(String name);

  int insertActor(Actor actor);

  int updateActor(Actor actor);
}
