package com.shan.tech.javlib.utils;

import com.shan.tech.javlib.consts.Constants;
import com.shan.tech.javlib.consts.RedisConst;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;


public class RedisUtils {

    public static void pushSpiderStartURL(ListOperations<String, String> listOperations, String spider, String url) {
        listOperations.leftPush(spider + Constants.COLON + RedisConst.SPIDER_START_URLS, url);
    }

    public static String getDomain(ValueOperations<String, String> valueOperations) {
        String domain = valueOperations.get(RedisConst.DOMAIN);
        return "http://www." + domain + ".com/cn";
    }
}
