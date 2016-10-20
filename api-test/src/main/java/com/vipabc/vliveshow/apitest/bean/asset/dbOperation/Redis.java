package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Processor;
import org.apache.log4j.Logger;
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
    Logger logger = Logger.getLogger(Redis.class);

    @Override
    public String process(Map<String, Object> extractionMap) {
        String rtnString = null;

        if (!isEmpty() && extractionMap.containsKey("redisConnection")) {

            JedisCluster jedisCluster = (JedisCluster) extractionMap.get("redisConnection");

            for (Map.Entry<RedisOperation, DBObject> entry : entrySet())
                rtnString = entry.getKey().setLogger(logger).setDBConnection(jedisCluster).execute(entry.getValue());
        }

        return rtnString;
    }

    @Override
    public String execute(Map<String, Object> extractionMap) {
        return process(extractionMap);
    }


    public Logger getLogger() {
        return logger;
    }

    public Redis setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }
}
