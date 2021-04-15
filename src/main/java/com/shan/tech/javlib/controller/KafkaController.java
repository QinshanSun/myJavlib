package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.engine.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  private KafkaProducer kafkaProducer;

  @GetMapping
  public String producer(@RequestParam("message") String message) {
    kafkaProducer.sendMessage(message);
    return "Message sent to the Kafka Topic java_in_use_topic Successfully";
  }

  @Autowired
  public void setKafkaProducer(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }
}
