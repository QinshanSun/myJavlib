package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.Video;
import org.apache.ibatis.annotations.Mapper;

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
}
