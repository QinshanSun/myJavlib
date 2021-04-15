package com.shan.tech.javlib.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

  private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  @KafkaListener(topics = "users.topic", groupId = "group_id")
  public void consume(String message) throws IOException {
    logger.info(String.format("#### -> Consumed message -> %s", message));
  }
}
