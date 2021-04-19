package com.shan.tech.javlib.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shan.tech.javlib.consts.KafkaConsts;
import com.shan.tech.javlib.pojo.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class KafkaConsumer {

  private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  @Autowired
  private ObjectMapper objectMapper;

  @KafkaListener(topics = "users.topic", groupId = "group_id")
  public void consume(String message) throws IOException {
    logger.info(String.format("#### -> Consumed message -> %s", message));
  }

  @KafkaListener(id = "genre", clientIdPrefix = "genre-batch",topics = {KafkaConsts.GENRE_TOPIC}, containerFactory = "batchContainerFactory")
  public void consume(@Payload List<String> genreList) {
    logger.info("topic.quick.batch  receive : ");
    for (String s : genreList) {
      try {
        Genre genre = objectMapper.readValue(s, Genre.class);
        logger.info("Genre:"+ genre.toString());
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }
}
