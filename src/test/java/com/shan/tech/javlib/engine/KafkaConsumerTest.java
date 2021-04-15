package com.shan.tech.javlib.engine;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaConsumerTest {

  private KafkaTemplate kafkaTemplate;


  @Test
  public void testDemo() throws InterruptedException {
    kafkaTemplate.send("users.topic", "this is my first demo");
    //休眠5秒，为了使监听器有足够的时间监听到topic的数据
    Thread.sleep(50000);
  }

  @Autowired
  public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }
}
