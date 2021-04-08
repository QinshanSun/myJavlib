package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.Genre;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GenreMapper {

  Optional<Genre> findById(Long id);

  Optional<Genre> findByLabel(String label);

  List<Genre> findGenresByName(String name);

  List<Genre> findAll();
}
