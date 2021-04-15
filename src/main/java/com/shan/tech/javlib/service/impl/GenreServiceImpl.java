package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.mapper.GenreMapper;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

  private GenreMapper genreMapper;

  @Override
  public Optional<Genre> findByLabel(String label) {
    return genreMapper.findByLabel(label);
  }

  @Override
  public List<Genre> findGenresByName(String name) {
    return genreMapper.findGenresByName(name);
  }

  @Override
  public List<Genre> findAll() {
    return genreMapper.findAll();
  }

  @Override
  public int insertGenre(Genre genre) {
    return genreMapper.insertGenre(genre);
  }

  @Override
  public int insertGenreList(List<Genre> genreList) {
    return genreMapper.insertGenreList(genreList);
  }

  @Autowired
  public void setGenreMapper(GenreMapper genreMapper) {
    this.genreMapper = genreMapper;
  }
}
