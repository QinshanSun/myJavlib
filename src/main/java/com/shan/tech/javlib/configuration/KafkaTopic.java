package com.shan.tech.javlib.configuration;

import com.shan.tech.javlib.consts.KafkaConsts;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopic {

  public NewTopic genreTopic() {
    return new NewTopic(KafkaConsts.GENRE_TOPIC, KafkaConsts.DEFAULT_PARTITION_NUM, KafkaConsts.DEFAULT_REPLICATION_NUM);
  }
}
