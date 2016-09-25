package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import leo.carnival.workers.prototype.Processor;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by leozhang on 9/25/16.
 * Bean of sql operation
 */
@SuppressWarnings("WeakerAccess")
public class Sql extends LinkedHashMap<SqlOperation, DBObject> implements Processor<Map<String, Object>, String> {
    @Override
    public String process(Map<String, Object> extractionMap) {

        String rtnString = null;

        if ( !isEmpty() && extractionMap.containsKey("sqlConnection")) {

            Connection connection = (Connection) extractionMap.get("sqlConnection");

            for (Map.Entry<SqlOperation, DBObject> entry : entrySet())
                rtnString = entry.getKey().setDBConnection(connection).execute(entry.getValue());
        }

        return rtnString;
    }

    @Override
    public String execute(Map<String, Object> extractionMap) {
        return process(extractionMap);
    }
}
