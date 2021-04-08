package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {
  Optional<Actor> findById(Long id);

  List<Actor> findAll();

  List<Actor> findActorsByName(String name);
}
