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

    List<Actor> findAll();

    List<Actor> findActorsByName(@Param("name") String name);

    int insertActor(Actor actor);

    int updateActor(Actor actor);

    Page<Actor> findByPage();

    int insertActorsForVideo(List<Actor> actorList, Video video);
}
