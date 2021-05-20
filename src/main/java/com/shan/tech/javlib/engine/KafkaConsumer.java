package com.shan.tech.javlib.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shan.tech.javlib.consts.KafkaConst;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.service.ActorService;
import com.shan.tech.javlib.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KafkaConsumer {

  private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  private GenreService genreService;

  private ActorService actorService;

  @KafkaListener(topics = "users.topic", groupId = "group_id")
  public void consume(String message) {
    logger.info(String.format("#### -> Consumed message -> %s", message));
  }

  @KafkaListener(id = "genre", clientIdPrefix = "genre-batch",topics = {KafkaConst.GENRE_TOPIC}, containerFactory = "batchContainerFactory")
  public void consumeGenre(@Payload List<String> genreList) {
    logger.info("topic.quick.batch  receive : ");
    for (String s : genreList) {
      try {
        Genre genre = objectMapper.readValue(s, Genre.class);
        int res = genreService.insertGenre(genre);
        logger.info("Genre: "+ genre.toString()+ ",success: "+ res);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }


  @KafkaListener(id = "actor", clientIdPrefix = "actor-batch",topics = {KafkaConst.ACTOR_TOPIC}, containerFactory = "batchContainerFactory")
  public void consumeActor(@Payload List<String> actorList) {
    logger.info("topic.quick.batch actor  receive : ");
    for (String s : actorList) {
      try {
        Actor actor = objectMapper.readValue(s, Actor.class);
        actor.setUpdatedDate(new Date());
        actor.setCreatedDate(new Date());
        int res = actorService.insertActor(actor);
        logger.info("Actor: "+ actor + ",success: "+ res);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
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
}
