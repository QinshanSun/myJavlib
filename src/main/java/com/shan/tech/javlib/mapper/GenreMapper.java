package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.Genre;

import java.util.List;

public interface GenreMapper {

  Genre findById(Long id);

  Genre findByLabel(String label);

  List<Genre> findGenresByName(String name);

  List<Genre> findAll();
}
