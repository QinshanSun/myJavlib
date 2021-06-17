package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.consts.RedisConst;
import com.shan.tech.javlib.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, String> listOperations;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @PostMapping("/actor")
    public void updateActor(@RequestParam(name = "type", required = false) String type) {
       String URL =  RedisUtils.getDomain(valueOperations);
       if (StringUtils.hasText(type)){
           RedisUtils.pushSpiderStartURL(listOperations, RedisConst.ACTOR_SPIDER,URL + RedisConst.ACTOR_PREFIX + type);
       }else {
           Stream.iterate('A', i -> ++i).limit(26).forEach(a->RedisUtils.pushSpiderStartURL(listOperations, RedisConst.ACTOR_SPIDER,URL + RedisConst.ACTOR_PREFIX + a.toString()));
       }
    }

    @PostMapping("/domain")
    public void updateDomain(@RequestParam(name = "domain") String domain){
        valueOperations.set(RedisConst.DOMAIN, domain);
    }

}
