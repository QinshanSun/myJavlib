package com.shan.tech.javlib.mapper;

import com.github.pagehelper.Page;
import com.shan.tech.javlib.pojo.Actor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ActorMapper {

    Optional<Actor> findById(Long id);

    Optional<Actor> findByLabel(String label);

    List<Actor> findAll();

    List<Actor> findActorsByName(@Param("name") String name);

    int insertActor(Actor actor);

    Page<Actor> findByPage();

}
