package com.shan.tech.javlib.engine;

import com.shan.tech.javlib.consts.Constants;
import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.service.ActorService;
import com.shan.tech.javlib.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//@EnableScheduling   // 1.开启定时任务
@EnableAsync // 2.开启多线程
public class ScheduledTask {

  private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
  @Autowired
  private HashOperations<String, String, Object> hashOperations;

  @Autowired
  private ListOperations<String, String> listOperations;

  @Autowired
  private ValueOperations<String, String> valueOperations;

  @Autowired
  private ActorService actorService;


  @Autowired
  private VideoService videoService;

  @Async
  @Scheduled(fixedDelay = 1000000)
  public void checkRedisVideoSpiderStatus() throws InterruptedException {
    Long number = listOperations.size(RedisConst.VIDEO_SPIDER + Constants.COLON + RedisConst.SPIDER_START_URLS);
    logger.info("The numbers of URL video spider need to process:" + number);
    if (number != null && number < RedisConst.SPIDER_URLS_NUMBER_LIMIT) {
      actorService.findOutOfDateActors();
    }
  }

  @Async
  @Scheduled(fixedDelay = 20000)
  public void checkRedisVideoDetailedInfoSpiderStatus() throws InterruptedException {
    Long number = listOperations.size(RedisConst.DETAILED_VIDEO_SPIDER + Constants.COLON + RedisConst.SPIDER_START_URLS);
    logger.info("The numbers of URL video spider need to process:" + number);
    if (number != null && number < RedisConst.SPIDER_URLS_NUMBER_LIMIT) {
      videoService.findOutOfDateVideos();
    }
  }
}
