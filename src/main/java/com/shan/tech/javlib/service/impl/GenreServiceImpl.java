package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.consts.ErrorMessage;
import com.shan.tech.javlib.consts.RedisConsts;
import com.shan.tech.javlib.mapper.GenreMapper;
import com.shan.tech.javlib.model.exception.NoFoundException;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

  private GenreMapper genreMapper;

  private RedisTemplate redisTemplate;

  @Override
  public Genre findByLabel(String label) {
    return genreMapper.findByLabel(label).orElseThrow(() -> new NoFoundException(String.format(ErrorMessage.NO_FOUND, Genre.class.getName(), label)));
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
    Optional<Genre> optionalGenre =  genreMapper.findByLabel(genre.getLabel());
    if(optionalGenre.isEmpty()){
      int result = genreMapper.insertGenre(genre);
      if(result == 1){
        redisTemplate.opsForSet().add(RedisConsts.GENRE_SET, genre);
        return result;
      }
    }
    return 0;
  }

  @Override
  public int insertGenreList(List<Genre> genreList) {
    return genreMapper.insertGenreList(genreList);
  }

  @Autowired
  public void setGenreMapper(GenreMapper genreMapper) {
    this.genreMapper = genreMapper;
  }

  @Autowired
  public void setRedisTemplate(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }
}
