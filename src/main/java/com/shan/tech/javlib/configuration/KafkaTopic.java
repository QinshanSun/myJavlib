package com.shan.tech.javlib.configuration;

import com.shan.tech.javlib.consts.KafkaConst;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopic {

  @Bean
  public NewTopic genreTopic() {
    return new NewTopic(KafkaConst.GENRE_TOPIC, KafkaConst.DEFAULT_PARTITION_NUM, KafkaConst.DEFAULT_REPLICATION_NUM);
  }

  @Bean
  public NewTopic actorTopic() {
    return new NewTopic(KafkaConst.ACTOR_TOPIC, KafkaConst.DEFAULT_PARTITION_NUM, KafkaConst.DEFAULT_REPLICATION_NUM);
  }

  @Bean
  public NewTopic videoTopic() {
    return new NewTopic(KafkaConst.VIDEO_TOPIC, KafkaConst.DEFAULT_PARTITION_NUM, KafkaConst.DEFAULT_REPLICATION_NUM);
  }

  @Bean
  public NewTopic videoDetailTopic() {
    return new NewTopic(KafkaConst.VIDEO_TOPIC_DETAIL, KafkaConst.DEFAULT_PARTITION_NUM, KafkaConst.DEFAULT_REPLICATION_NUM);
  }
}
