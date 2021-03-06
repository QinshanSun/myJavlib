package com.shan.tech.javlib.service;

import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;

import java.util.List;
import java.util.Optional;

public interface VideoService {

  Optional<Video> findById(Long id);

  List<Video> findOutOfDateVideos();

  List<Video> findVideosByTitle(String title);

  Optional<Video> findDetailedVideoById(Long id);

  int insertVideo(Video video);

  int updateVideo(Video video);

  int insertVideoList(List<Video> videoList);

  int insertGenresForVideo(List<Genre> genreList, Video video);

  int insertActorsForVideo(List<Actor> actorList, Video video);
}
