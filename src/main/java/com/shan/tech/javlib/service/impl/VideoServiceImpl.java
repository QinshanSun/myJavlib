package com.shan.tech.javlib.service.impl;

import com.shan.tech.javlib.consts.Constants;
import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.mapper.ActorMapper;
import com.shan.tech.javlib.mapper.GenreMapper;
import com.shan.tech.javlib.mapper.VideoMapper;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;
import com.shan.tech.javlib.service.VideoService;
import com.shan.tech.javlib.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

  private VideoMapper videoMapper;

  private ActorMapper actorMapper;

  private GenreMapper genreMapper;

  @Autowired
  private SetOperations<String, String> stringSetOperations;


  @Autowired
  private ListOperations<String, String> listOperations;


  @Autowired
  private ValueOperations<String, String> valueOperations;

  @Override
  public Optional<Video> findById(Long id) {
    return videoMapper.findById(id);
  }

  @Override
  public List<Video> findOutOfDateVideos() {
    List<Video> videoList = videoMapper.findOutOfDateVideos();
    if (videoList.size() > 0){
      videoList.stream().parallel().map(video -> video.getLabel().replace(".","")).collect(Collectors.toSet()).forEach(label ->
              RedisUtils.pushSpiderStartURL(listOperations, RedisConst.DETAILED_VIDEO_SPIDER, RedisUtils.getDomain(valueOperations) + label));
    }
    return videoList;
  }

  @Override
  public List<Video> findVideosByTitle(String title) {
    return videoMapper.findVideosByTitle(title);
  }

  @Override
  public Optional<Video> findDetailedVideoById(Long id) {
    return videoMapper.findDetailedVideoById(id);
  }

  @Override
  @Transactional
  public int insertVideo(Video video) {
    if (video == null)
      return 0;
    int res = videoMapper.insertVideo(video);
    stringSetOperations.add(RedisConst.SET_ALL_VIDEO, video.getLabel());
    return res;
  }

  @Override
  @Transactional
  public int updateVideo(Video video) {
    video.setYear(video.getReleased().getYear());
    videoMapper.updateVideo(video);
    Optional<Video> updateVideoOptional = videoMapper.findByLabel(video.getLabel());
    if (updateVideoOptional.isPresent()) {
      if (!CollectionUtils.isEmpty(video.getActorList())){
        List<String> actorLabels = video.getActorList().stream().map(Actor::getLabel).collect(Collectors.toList());
        List<Actor> actorList = actorMapper.findByLabels(actorLabels);
        actorMapper.insertActorsForVideo(actorList, updateVideoOptional.get());
      }
      if (!CollectionUtils.isEmpty(video.getGenreList())){
        List<String> genreLabels = video.getGenreList().stream().map(Genre::getLabel).collect(Collectors.toList());
        List<Genre> genreList = genreMapper.findByLabels(genreLabels);
        if (!CollectionUtils.isEmpty(genreList)){
          genreMapper.insertGenresForVideo(genreList, updateVideoOptional.get());
        }
      }
    }
    return 1;
  }

  @Override
  @Transactional
  public int insertVideoList(List<Video> videoList) {
    if (CollectionUtils.isEmpty(videoList))
      return 0;
    int res = videoMapper.insertVideoList(videoList);
    videoList.forEach(video -> stringSetOperations.add(RedisConst.SET_ALL_VIDEO, video.getLabel()));
    return res;
  }


  @Override
  public int insertGenresForVideo(List<Genre> genreList, Video video) {
    return videoMapper.insertGenresForVideo(genreList, video);
  }

  @Override
  public int insertActorsForVideo(List<Actor> actorList, Video video) {
    return videoMapper.insertActorsForVideo(actorList, video);
  }


  @Autowired
  public void setVideoMapper(VideoMapper videoMapper) {
    this.videoMapper = videoMapper;
  }

  @Autowired
  public void setActorMapper(ActorMapper actorMapper) {
    this.actorMapper = actorMapper;
  }

  @Autowired
  public void setGenreMapper(GenreMapper genreMapper) {
    this.genreMapper = genreMapper;
  }
}
