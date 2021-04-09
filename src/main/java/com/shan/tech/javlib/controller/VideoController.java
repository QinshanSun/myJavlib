package com.shan.tech.javlib.controller;


import com.shan.tech.javlib.pojo.Video;
import com.shan.tech.javlib.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {

  private VideoService videoService;

  @GetMapping
  public Video getVideoById(@RequestParam(name = "Id") Long Id) {
    return videoService.findById(Id).orElseThrow();
  }


  @Autowired
  public void setVideoService(VideoService videoService) {
    this.videoService = videoService;
  }
}
