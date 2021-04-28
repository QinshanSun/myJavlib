package com.shan.tech.javlib.engine;

import com.shan.tech.javlib.consts.KafkaConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
  private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
  private static final String TOPIC = "users.topic";


  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message) {
    logger.info(String.format("#### -> Producing message -> %s", message));
    kafkaTemplate.send(KafkaConst.GENRE_TOPIC, message);
  }

  @Autowired
  public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }
}
