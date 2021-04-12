package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {
  Optional<Actor> findById(Long id);

  Optional<Actor> findByLabel(String label);

  List<Actor> findAll();

  List<Actor> findActorsByName(String name);

  int insertActor(Actor actor);
}
