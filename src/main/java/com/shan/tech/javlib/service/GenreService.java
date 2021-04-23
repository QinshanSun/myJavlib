package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Genre;

import java.util.List;

public interface GenreService {

  Genre findByLabel(String label);

  List<Genre> findGenresByName(String name);

  List<Genre> findAll();

  int insertGenre(Genre genre);

  int insertGenreList(List<Genre> genreList);
}
