package com.redis.util;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/10/25.
 */
public class Main {

    @Autowired
    private static RedisCacheManager redisCacheManager;

    public static void main(String[] args) {
        redisCacheManager = new RedisCacheManager();
        System.out.print(redisCacheManager.hasKey("redisKey"));

    }
}
