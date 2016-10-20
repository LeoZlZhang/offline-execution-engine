package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.engineData.testData.Bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of DBOperation
 */
@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class DBOperation extends Bean<Map<String, Object>, String> implements Serializable {

    private Mongo mongo;
    private Redis redis;
    private Sql sql;


    @Override
    public String execute(Map<String, Object> extractionMap) {
        return
                mongo != null ? mongo.setLogger(logger).process(extractionMap) :
                        redis != null ? redis.setLogger(logger).process(extractionMap) :
                                sql != null ? sql.setLogger(logger).process(extractionMap) : null;
    }


    /**
     * Getter
     */
    public Mongo getMongo() {
        return mongo;
    }

    public Redis getRedis() {
        return redis;
    }

    public Sql getSql() {
        return sql;
    }

    /**
     * Setter
     */
    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public void setSql(Sql sql) {
        this.sql = sql;
    }
}
