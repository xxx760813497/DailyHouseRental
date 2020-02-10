package com.xmj.demo.redis;

import com.google.gson.Gson;
import com.xmj.demo.entity.House;
import com.xmj.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class HouseRedis {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    public void set(String key,Long time,House house){
        Gson gson=new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(house),time,TimeUnit.MINUTES);
    }

    public House get(String key){
        Gson gson=new Gson();
        House house=null;
        String json=redisTemplate.opsForValue().get(key);
        if (!json.equals("")){
            house=gson.fromJson(json,House.class);
        }
        return house;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }

}
