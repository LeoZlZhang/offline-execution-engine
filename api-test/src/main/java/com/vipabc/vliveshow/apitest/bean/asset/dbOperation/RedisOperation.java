package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

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

            logger.info(String.format("[%d] %s %s with %s", Thread.currentThread().getId(), "RedisOperation", this.name(), dbObject.toString()));
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

            logger.info(String.format("[%d] %s %s with %s", Thread.currentThread().getId(), "RedisOperation", this.name(), dbObject.toString()));
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

            logger.info(String.format("[%d] %s %s with %s", Thread.currentThread().getId(), "RedisOperation", this.name(), dbObject.toString()));
            return getDBConnection().del(dbObject.getCriteria().get("key").toString()).toString();
        }
    };

    private static final Logger logger = Logger.getLogger(RedisOperation.class);
    private JedisCluster jedisCluster;

    public RedisOperation setDBConnection(JedisCluster dbConnection) {
        this.jedisCluster = dbConnection;
        return this;
    }

    public JedisCluster getDBConnection(){
        return jedisCluster;
    }

}
