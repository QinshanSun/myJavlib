package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

  Optional<Genre> findByLabel(String label);

  List<Genre> findGenresByName(String name);

  List<Genre> findAll();

  int insertGenre(Genre genre);

  int insertGenreList(List<Genre> genreList);
}
