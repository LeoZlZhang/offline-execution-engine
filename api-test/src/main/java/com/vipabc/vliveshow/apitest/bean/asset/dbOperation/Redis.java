package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Processor;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of redis operation
 */
@SuppressWarnings("WeakerAccess")
public class Redis extends LinkedHashMap<RedisOperation, DBObject> implements Processor<Map<String, Object>, String>, Serializable {
    @Override
    public String process(Map<String, Object> extractionMap) {
        String rtnString = null;

        if (!isEmpty() && extractionMap.containsKey("redisConnection")) {

            JedisCluster jedisCluster = (JedisCluster) extractionMap.get("redisConnection");

            for (Map.Entry<RedisOperation, DBObject> entry : entrySet())
                rtnString = entry.getKey().setDBConnection(jedisCluster).execute(entry.getValue());
        }

        return rtnString;
    }

    @Override
    public String execute(Map<String, Object> extractionMap) {
        return process(extractionMap);
    }
}
