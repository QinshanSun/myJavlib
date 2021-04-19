package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.engine.KafkaProducer;
import com.shan.tech.javlib.pojo.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  private KafkaProducer kafkaProducer;

  @GetMapping
  public String producer(@RequestParam("message") String message) {
    for (int i = 0; i < 12; i++) {
      kafkaProducer.sendMessage(message);
    }
    return "Message sent to the Kafka Topic java_in_use_topic Successfully";
  }

  @GetMapping("/genre")
  public String producer(@RequestBody Genre genre) {
    for (int i = 0; i < 12; i++) {
      kafkaProducer.sendMessage(genre.toString());
    }
    return "Message sent to the Kafka Topic java_in_use_topic Successfully";
  }


  @Autowired
  public void setKafkaProducer(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }
}
