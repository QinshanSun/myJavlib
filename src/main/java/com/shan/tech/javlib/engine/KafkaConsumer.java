package com.shan.tech.javlib.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shan.tech.javlib.consts.KafkaConst;
import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.pojo.Video;
import com.shan.tech.javlib.service.ActorService;
import com.shan.tech.javlib.service.GenreService;
import com.shan.tech.javlib.service.VideoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KafkaConsumer {

  private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  private GenreService genreService;

  private ActorService actorService;

  private VideoService videoService;

  @Autowired
  private SetOperations<String, String> stringSetOperations;

  @KafkaListener(topics = "users.topic", groupId = "group_id")
  public void consume(String message) {
    logger.info(String.format("#### -> Consumed message -> %s", message));
  }

  @KafkaListener(id = "genre", clientIdPrefix = "genre-batch", topics = {KafkaConst.GENRE_TOPIC}, containerFactory = "batchContainerFactory")
  public void consumeGenre(@Payload List<String> genreList) {
    logger.info("topic.quick.batch  receive : ");
    for (String s : genreList) {
      try {
        Genre genre = objectMapper.readValue(s, Genre.class);
        int res = genreService.insertGenre(genre);
        logger.info("Genre: " + genre.toString() + ",success: " + res);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }


  @KafkaListener(id = "actor", clientIdPrefix = "actor-batch", topics = {KafkaConst.ACTOR_TOPIC}, containerFactory = "batchContainerFactory")
  public void consumeActor(@Payload List<String> actorList) {
    logger.info("topic.quick.batch actor  receive : ");
    for (String s : actorList) {
      try {
        Actor actor = objectMapper.readValue(s, Actor.class);
        int res = 0;
        List<Actor> actors = actorService.findByLabel(actor.getLabel());
        if (actors.size() > 0) {
          for (Actor existActor : actors) {
            if (existActor.getName().equals(actor.getName())) {
              actor.setId(existActor.getId());
              actor.setCreatedDate(existActor.getCreatedDate());
              res = actorService.updateActor(actor);
            }
          }
        }
        if (res == 0) {
          actor.setCreatedDate(new Date());
          res = actorService.insertActor(actor);
        }
        logger.info("Actor: " + actor + ",success: " + res);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }

  @KafkaListener(id = "video", clientIdPrefix = "video-batch", topics = {KafkaConst.VIDEO_TOPIC}, containerFactory = "batchContainerFactory")
  public void consumeVideo(@Payload List<String> videoList) {
    logger.info("topic.quick.batch video  receive : ");
    List<Video> videos = new ArrayList<>();
    for (String s : videoList) {
      try {
        Video video = objectMapper.readValue(s, Video.class);
        if (!stringSetOperations.isMember(RedisConst.SET_ALL_VIDEO, video.getLabel())) {
          video.setCreatedDate(new Date());
          videos.add(video);
        }
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    int res = videoService.insertVideoList(videos);
    logger.info("Video: " + videos + ", success: " + res);
  }


  @KafkaListener(id = "video_detail", clientIdPrefix = "video-detail-batch", topics = {KafkaConst.VIDEO_TOPIC_DETAIL}, containerFactory = "batchContainerFactory")
  public void consumeVideoDetail(@Payload String videoString) {
    logger.info("topic.quick.batch video  receive : ");
    logger.info("Video: " + videoString);
    try {
      Video video = objectMapper.readValue(videoString, Video.class);
      int res = videoService.updateVideo(video);
      logger.info("Video: " + video + ", success: " + res);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }


  @Autowired
  public void setGenreService(GenreService genreService) {
    this.genreService = genreService;
  }

  @Autowired
  public void setActorService(ActorService actorService) {
    this.actorService = actorService;
  }

  @Autowired
  public void setVideoService(VideoService videoService) {
    this.videoService = videoService;
  }
}
