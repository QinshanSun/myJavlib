package com.shan.tech.javlib.controller;


import com.shan.tech.javlib.consts.ErrorMessage;
import com.shan.tech.javlib.model.exception.NoFoundException;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;
import com.shan.tech.javlib.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

  private VideoService videoService;

  @GetMapping
  public Video getVideoById(@RequestParam(name = "Id") Long Id) {
    return videoService.findById(Id).orElseThrow(() -> new NoFoundException(String.format(ErrorMessage.NO_FOUND, Video.class.getName(), "ID:" + Id)));
  }

  @GetMapping("/genres")
  public List<Video> getVideoListByGenres(@RequestParam(name = "genres") List<Genre> genreList) {
    return Collections.EMPTY_LIST;
  }

  @GetMapping("/name")
  public List<Video> getVideoListByName(@RequestParam(name = "title") String title) {
    return videoService.findVideosByTitle(title);
  }

  @Autowired
  public void setVideoService(VideoService videoService) {
    this.videoService = videoService;
  }
}
