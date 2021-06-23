package com.shan.tech.javlib.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Video;

import java.util.List;
import java.util.Optional;

public interface ActorService {
  Optional<Actor> findById(Long id);

  List<Actor> findByLabel(String label);

  List<Actor> findAll();

  PageInfo<Actor> findActorsByPage(int pageNum, int pageSize);

  List<Actor> findActorsByName(String name);

  List<Actor> findOutOfDateActors();

  int insertActor(Actor actor);

  int updateActor(Actor actor);

  int updateActors(List<Actor> actorList);

  int insertActorsForVideo(List<Actor> actorList, Video video);
}
