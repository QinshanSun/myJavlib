package com.shan.tech.javlib.consts;

public class RedisConst {

  public final static String HASH_ALL_USER = "HASH_ALL_USER";

  public final static String HASH_ALL_GENRE = "HASH_ALL_GENRE";

  public final static String HASH_ALL_ACTOR = "HASH_ALL_ACTOR";

  public final static String SET_ALL_VIDEO = "SET_ALL_VIDEO";

  //spiders related constant
  public final static int SPIDER_URLS_NUMBER_LIMIT = 50;

  public final static String SPIDER_START_URLS = "start_urls";

  public final static String VIDEO_SPIDER = "video_spider";

  public final static String DETAILED_VIDEO_SPIDER = "detailed_video_spider";

  public final static String ACTOR_SPIDER = "actor_spider";

  public final static String GENRE_SPIDER = "genre_spider";

  public final static String DOMAIN = "spider_domain";

  public final static String ACTOR_PREFIX = "/star_list.php?prefix=";

  public final static String GENRE_START_URL = "genres.php";

  public final static String VIDEO_MODE = "&mode=2&"; // mode 2 means all videos

  public final static String VIDEO_URL_DELIMITER = "?";

  public final static String URL_START = "http://www.";

  public final static String URL_END = ".com/cn";

  public final static String ACTOR = "ACTOR";

  public final static String USER = "USER";


  //Scheduled Task Related
  public final static String ENABLE_SCHEDULED_TASK = "ENABLE_SCHEDULED_TASK";
}
