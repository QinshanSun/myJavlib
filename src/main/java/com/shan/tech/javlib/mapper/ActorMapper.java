package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.Actor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ActorMapper {

    Optional<Actor> findById(Long id);

    List<Actor> findAll();

    List<Actor> findActorsByName(String name);

}
