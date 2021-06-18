package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.configuration.RedisCacheable;
import com.shan.tech.javlib.consts.ErrorMessage;
import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.mapper.GenreMapper;
import com.shan.tech.javlib.model.exception.NoFoundException;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;
import com.shan.tech.javlib.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

  private GenreMapper genreMapper;

  private HashOperations<String, String, Object> hashOperations;

  @Override
  @RedisCacheable(type = Genre.class)
  public Genre findByLabel(String label) {
    return genreMapper.findByLabel(label).orElseThrow(() -> new NoFoundException(String.format(ErrorMessage.NO_FOUND, Genre.class.getName(), label)));
  }

  @Override
  @RedisCacheable(type = Genre.class)
  public List<Genre> findGenresByName(String name) {
    List<Genre> genreList;
    /*
    List<Genre> redisGenreList = new ArrayList<>();
    hashOperations.keys(RedisConst.HASH_ALL_GENRE).stream().filter(hashKey -> hashKey.contains(name)).forEach(hashKey -> redisGenreList.add((Genre) hashOperations.get(RedisConst.HASH_ALL_GENRE, hashKey)));
    */
    genreList = genreMapper.findGenresByName(name);
    genreList.forEach(genre -> hashOperations.put(RedisConst.HASH_ALL_GENRE, genre.getName(), genre));
    return genreList;
  }

  @Override
  public List<Genre> findAll() {
    return genreMapper.findAll();
  }

  @Override
  public int insertGenre(Genre genre) {
    Optional<Genre> optionalGenre = genreMapper.findByLabel(genre.getLabel());
    if (optionalGenre.isEmpty()) {
      int result = genreMapper.insertGenre(genre);
      if (result == 1) {
        hashOperations.put(RedisConst.HASH_ALL_GENRE, genre.getName(), genre);
        return result;
      }
    }
    return 0;
  }

  @Override
  public int insertGenreList(List<Genre> genreList) {
    return genreMapper.insertGenreList(genreList);
  }

  @Override
  public int insertGenresForVideo(List<Genre> genreList, Video video) {
    return genreMapper.insertGenresForVideo(genreList, video);
  }

  @Autowired
  public void setGenreMapper(GenreMapper genreMapper) {
    this.genreMapper = genreMapper;
  }

  @Autowired
  public void setHashOperations(HashOperations<String, String, Object> hashOperations) {
    this.hashOperations = hashOperations;
  }
}
