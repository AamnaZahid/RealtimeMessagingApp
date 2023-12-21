package com.message.app.servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisListService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisListService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void pushDataToList(String key, String value) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(key, value);
    }
}
