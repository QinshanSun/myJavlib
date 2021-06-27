package com.shan.tech.javlib.mapper;

import com.github.pagehelper.Page;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ActorMapper {

  Optional<Actor> findById(Long id);

  List<Actor> findByLabel(String label);

  List<Actor> findByLabels(List<String> labels);

  List<Actor> findAll();

  List<Actor> findActorsByName(@Param("name") String name);

  List<Actor> findOutOfDateActors();

  int insertActor(Actor actor);

  int updateActor(Actor actor);

  int updateActors(List<Actor> actorList);

  Page<Actor> findByPage();

  int insertActorsForVideo(List<Actor> actorList, Video video);
}
