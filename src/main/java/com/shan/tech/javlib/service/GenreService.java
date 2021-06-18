package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;

import java.util.List;

public interface GenreService {

  Genre findByLabel(String label);

  List<Genre> findGenresByName(String name);

  List<Genre> findAll();

  int insertGenre(Genre genre);

  int insertGenreList(List<Genre> genreList);

  int insertGenresForVideo(List<Genre> genreList, Video video);
}
