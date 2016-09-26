package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Executor;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leo_zlzhang on 9/19/2016.
 * Redis operation
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public enum RedisOperation implements Serializable, Executor<DBObject, String> {

    get {
        @Override
        public String execute(DBObject dbObject) {
            if(dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if(dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid get operation to redis, criteria should contain one entry with valid key");
            if(!dbObject.getCriteria().containsKey("key"))
                throw new RuntimeException("Not define the key to query");


            return getDBConnection().get(dbObject.getCriteria().get("key").toString());
        }
    },
    set{
        @Override
        public String execute(DBObject dbObject) {
            if(dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if(dbObject.getValues() == null || dbObject.getValues().isEmpty())
                throw new RuntimeException("Invalid set operation to redis, criteria should contain one key value pair");
            if(!dbObject.getValues().containsKey("key"))
                throw new RuntimeException("Not define the key to set");
            if(!dbObject.getValues().containsKey("value"))
                throw new RuntimeException("Not define the value to set");


            return getDBConnection().set(dbObject.getValues().get("key").toString(), dbObject.getValues().get("value").toString());
        }
    },
    del{
        @Override
        public String execute(DBObject dbObject) {
            if(dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if(dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid del operation to redis, criteria should contain one key value pair");
            if(!dbObject.getCriteria().containsKey("key"))
                throw new RuntimeException("Not define the key to delete");

            return getDBConnection().del(dbObject.getCriteria().get("key").toString()).toString();
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
