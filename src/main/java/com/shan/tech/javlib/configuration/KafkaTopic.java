package com.shan.tech.javlib.configuration;

import com.shan.tech.javlib.consts.KafkaConst;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopic {

  public NewTopic genreTopic() {
    return new NewTopic(KafkaConst.GENRE_TOPIC, KafkaConst.DEFAULT_PARTITION_NUM, KafkaConst.DEFAULT_REPLICATION_NUM);
  }
}
