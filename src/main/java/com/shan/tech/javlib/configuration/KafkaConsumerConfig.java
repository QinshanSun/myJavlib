package com.shan.tech.javlib.configuration;

import com.shan.tech.javlib.consts.KafkaConst;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String kafkaServers;


  @Bean
  public ConsumerFactory<String, String> defaultConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  public Map<String, Object> batchConsumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaConst.MAX_POLL_RECORDS_CONFIG);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String>
            factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(defaultConsumerFactory());
    return factory;
  }

  @Bean("batchContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaBatchListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String>
            factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(batchConsumerProps()));
    factory.setConcurrency(KafkaConst.CURRENCY_NUM);
    factory.setBatchListener(true);
    return factory;
  }
}
