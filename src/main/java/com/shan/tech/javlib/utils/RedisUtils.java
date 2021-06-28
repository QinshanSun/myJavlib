package com.shan.tech.javlib.utils;

import com.shan.tech.javlib.consts.Constants;
import com.shan.tech.javlib.consts.RedisConst;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;


public class RedisUtils {

    public static void pushSpiderStartURL(ListOperations<String, String> listOperations, String spider, String url) {
        listOperations.leftPush(spider + Constants.COLON + RedisConst.SPIDER_START_URLS, url);
    }

    public static String getDomain(ValueOperations<String, String> valueOperations) {
        String domain = valueOperations.get(RedisConst.DOMAIN);
        return RedisConst.URL_START + domain + RedisConst.URL_END;
    }

    public static String buildVideoURLWithMode(String url, String label){
        final String[] video_url_parts = StringUtils.split(label, RedisConst.VIDEO_URL_DELIMITER);
        assert video_url_parts != null;
        assert video_url_parts.length == 2;
        return url + Constants.SLASH + video_url_parts[0] + RedisConst.VIDEO_URL_DELIMITER + RedisConst.VIDEO_MODE + video_url_parts[1];
    }

    public static String buildVideoDetailURL(String url, String label){
        return url + Constants.SLASH + label;
    }

    public static String buildGenreURL(String url, String label){
        return url + Constants.SLASH + label;
    }
}
