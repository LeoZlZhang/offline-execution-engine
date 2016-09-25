package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import com.mongodb.DB;
import leo.carnival.workers.prototype.Processor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of mongo operation
 */
@SuppressWarnings("WeakerAccess")
public class Mongo extends LinkedHashMap<MongoOperation, DBObject> implements Processor<Map<String, Object> , String> {
    @Override
    public String process(Map<String, Object> extractionMap) {

        String rtnString = null;

        if ( !isEmpty() && extractionMap.containsKey("mongoConnection")) {

            DB dbConnection = (DB) extractionMap.get("mongoConnection");

            for (Map.Entry<MongoOperation, DBObject> entry : entrySet())
                //last transaction effective
                rtnString = entry.getKey().setDBConnection(dbConnection).execute(entry.getValue());
        }
        return rtnString;
    }

    @Override
    public String execute(Map<String, Object> extractionMap) {
        return process(extractionMap);
    }
}
