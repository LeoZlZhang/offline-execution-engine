package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Executor;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leo_zlzhang on 9/19/2016.
 * Redis operation
 */
@SuppressWarnings("unused")
public enum RedisOperation implements Serializable, Executor<DBObject, String> {

    get {
        @Override
        public String execute(DBObject dbObject) {
            if(dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if(dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid get operation to redis, criteria should contain one entry with valid key");

            return getDBConnection().get(dbObject.getCriteria().keySet().iterator().next());
        }
    },
    set{
        @Override
        public String execute(DBObject dbObject) {
            if(dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if(dbObject.getValues() == null || dbObject.getValues().isEmpty())
                throw new RuntimeException("Invalid set operation to redis, criteria should contain one key value pair");

            Map.Entry<String, Object> entry2Set = dbObject.getValues().entrySet().iterator().next();
            return getDBConnection().set(entry2Set.getKey(), entry2Set.getValue().toString());
        }
    },
    del{
        @Override
        public String execute(DBObject dbObject) {
            if(dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if(dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid del operation to redis, criteria should contain one key value pair");

            Map.Entry<String, Object> entry2Set = dbObject.getCriteria().entrySet().iterator().next();
            return getDBConnection().del(entry2Set.getKey()).toString();
        }
    };
    private JedisCluster jedisCluster;

    public RedisOperation setDBConnection(JedisCluster dbConnection) {
        this.jedisCluster = dbConnection;
        return this;
    }

    public JedisCluster getDBConnection(){
        return jedisCluster;
    }

}
