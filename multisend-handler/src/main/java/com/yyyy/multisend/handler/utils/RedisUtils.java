package com.yyyy.multisend.handler.utils;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author isADuckA
 * @Date 2023/4/27 20:22
 * redis使用工具
 */
@Component
public class RedisUtils {

    @Autowired
    StringRedisTemplate redisTemplate;


    /**
     * 根据传进来的key,查找到相对应的value,
     * @param keys
     * @return
     */
    public Map<String,String> mget(Set<String> keys){
        Map<String,String> map=new HashMap<>();
        for(String key:keys){
            map.put(key,getKey(key));
        }
        return map;
    }


    /**
     * 获取当前key的值
     * @param key
     * @return
     */
    public String getKey(String key){
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 执行指定的lua脚本返回执行结果
     * --KEYS[1]: 限流 key
     * --ARGV[1]: 限流窗口
     * --ARGV[2]: 当前时间戳（作为score）
     * --ARGV[3]: 阈值
     * --ARGV[4]: score 对应的唯一value
     *
     * @param redisScript
     * @param args
     * @return 返回false就是超过阈值
     */
    public Boolean execLimitLua(RedisScript<Long> redisScript,String key, String... args) {

        Long execute = redisTemplate.execute(redisScript, Collections.singletonList(key), args);
        System.out.println("execute:"+execute);

        if (execute == null ||"0".equals(execute.toString())) {
            System.out.println("没有超过阈值");
            return false;
        }
        System.out.println("超过阈值");
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(key);
        System.out.println("过期时间"+expire);
        return true;
    }


    public void updateValue(Map<String,Integer> map){
        //过期时间
        long lineTime = DateUtil.endOfDay(new Date()).getTime() - DateUtil.current();
        for (String key:map.keySet()){
            //更新key值和它的过期时间
            redisTemplate.opsForValue().set(key, String.valueOf(map.get(key)),lineTime/1000, TimeUnit.SECONDS);
        }

    }

}
