package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface VideoMapper {

  Optional<Video> findById(Long id);

  Optional<Video> findByLabel(String label);

  List<Video> findAll();

  List<Video> findVideosByTitle(String title);

  Optional<Video> findDetailedVideoById(Long id);

  int insertVideo(Video video);

  int updateVideo(Video video);

  int insertVideoList(List<Video> videoList);

  int insertGenresForVideo(@Param("genreList") List<Genre> genreList, @Param("video") Video video);

  int insertActorsForVideo(@Param("actorList") List<Actor> actorList, @Param("video") Video video);
}
