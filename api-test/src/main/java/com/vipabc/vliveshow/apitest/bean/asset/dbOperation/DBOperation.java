package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Processor;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of DBOperation
 */
@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class DBOperation implements Serializable, Processor<Map<String, Object>, String> {

    private Mongo mongo;
    private Redis redis;
    private Sql sql;

    @Override
    public String process(Map<String, Object> extractionMap) {
        return
                mongo != null ? mongo.process(extractionMap) :
                        redis != null ? redis.process(extractionMap) :
                                sql != null ? sql.process(extractionMap) : null;
    }

    @Override
    public String execute(Map<String, Object> extractionMap) {
        return process(extractionMap);
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
