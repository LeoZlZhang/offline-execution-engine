package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Processor;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of DBOperation
 */
@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class DBOperation implements Processor<Map<String, Object>, String>, Serializable {

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
}
