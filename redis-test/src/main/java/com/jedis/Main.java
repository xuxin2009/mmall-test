package com.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/25.
 */
public class Main {

    public static void main(String[] args) {
        //连接redis
        Jedis jedis = new Jedis("127.0.01",6379);
        System.out.print("连接成功");
        //密码验证
        jedis.auth("123456");
        //查看服务启动
        System.out.print("查看服务是都启动："+jedis.ping());

        //String字符串
        jedis.set("redisKey","myValue");
        System.out.println("String字符串获取redisKey:"+jedis.get("redisKey"));
        //list

//        jedis.lpush("test-list","java");
//        jedis.lpush("test-list","c++");
//        jedis.lpush("test-list","php");
//        List<String> listCache = jedis.lrange("test-list",0,100);
//        for(int i = 0;i < listCache.size();i++)
//        {
//            System.out.println("list:"+listCache.get(i));
//        }
//        System.out.print(listCache.size());
        // Map
        Map<String ,String> testMap = new HashMap<String, String>();
        testMap.put("xiaoming","man");
        testMap.put("xiaoHua","woman");
        testMap.put("xiaoLi","man");
        jedis.hmset("test-hash",testMap);

        Map<String,String> hashData = jedis.hgetAll("test-hash");
        System.out.println("hash缓存集合输出"+hashData);
        System.out.println("hash缓存集合长度"+hashData.size());

        //set(无序，不重复)
        jedis.sadd("test-set","语文");
        jedis.sadd("test-set","数学");
        jedis.sadd("test-set","英语");

        Set<String> setCache = jedis.smembers("test-set");
        for (String setStr: setCache)
        {
            System.out.println("set集合缓存输出:"+setStr);
        }
        System.out.println("set长度:"+setCache.size());

        //zset(有序，不重复)
        jedis.zadd("test-zset",1,"张三");
        Set<String> zsetCache = jedis.zrange("test-zset",0,10);
        for (String str: zsetCache)
        {
            System.out.println("zset集合缓存输出"+str);
        }



    }


}
