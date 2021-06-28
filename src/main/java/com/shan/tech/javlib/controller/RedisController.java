package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.consts.Constants;
import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.service.ActorService;
import com.shan.tech.javlib.service.VideoService;
import com.shan.tech.javlib.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/redis")
public class RedisController {

  @Autowired
  private HashOperations<String, String, Object> hashOperations;

  @Autowired
  private ListOperations<String, String> listOperations;

  @Autowired
  private ValueOperations<String, String> valueOperations;

  //domain URL
  private static String url;

  @Autowired
  private ActorService actorService;

  @Autowired
  private VideoService videoService;

  @PostMapping("/actor")
  public void updateActor(@RequestParam(name = "type", required = false) String type) {
    if (StringUtils.hasText(type)) {
      RedisUtils.pushSpiderStartURL(listOperations, RedisConst.ACTOR_SPIDER, url + RedisConst.ACTOR_PREFIX + type);
    } else {
      //update actors from A to Z
      Stream.iterate('A', i -> ++i).limit(26).forEach(a -> RedisUtils.pushSpiderStartURL(listOperations, RedisConst.ACTOR_SPIDER, url + RedisConst.ACTOR_PREFIX + a.toString()));
    }
  }


  @PostMapping("/domain")
  public void updateDomain(@RequestParam(name = "domain") String domain) {
    valueOperations.set(RedisConst.DOMAIN, domain);
    // update URL by domain since it had been changed.
    url = RedisUtils.getDomain(valueOperations);
  }

  @PostMapping("/genre")
  public void updateGenre() {
    String genreURL = RedisUtils.buildGenreURL(url, RedisConst.GENRE_START_URL);
    RedisUtils.pushSpiderStartURL(listOperations, RedisConst.GENRE_SPIDER, genreURL);
  }

  @PostMapping("/video")
  public void updateVideo(@RequestParam(name = "label", required = false) String label) {
    if (StringUtils.hasText(label)) {
      RedisUtils.pushSpiderStartURL(listOperations, RedisConst.VIDEO_SPIDER, RedisUtils.buildVideoURLWithMode(url, label));
    } else {
      // todo need to find another smart way to update the video
      List<Actor> actorList = actorService.findOutOfDateActors();
    }
  }

  @PostMapping("/video/detail")
  public void updateVideoDetail(@RequestParam(name = "label", required = false) String label) {
    if (StringUtils.hasText(label)) {
      String videoDetailURL = RedisUtils.buildVideoDetailURL(url, label);
      RedisUtils.pushSpiderStartURL(listOperations, RedisConst.DETAILED_VIDEO_SPIDER, videoDetailURL);
    } else {
      // todo need to find another smart way to update the video

    }
  }

  /**
   * initial the properties when initial the (like: URL)
   */
  @PostConstruct
  public void postConstruct() {
    if (!StringUtils.hasLength(url)) {
      url = RedisUtils.getDomain(valueOperations);
    }
  }
}
