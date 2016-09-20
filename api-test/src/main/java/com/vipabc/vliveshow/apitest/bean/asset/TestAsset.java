package com.vipabc.vliveshow.apitest.bean.asset;


import com.google.api.client.http.HttpResponse;
import com.mongodb.DB;
import com.vipabc.vliveshow.apitest.Util.JsonExtractor;
import com.vipabc.vliveshow.apitest.bean.asset.assertions.AssertType;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.DBObject;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.MongoOperation;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.SqlOperation;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.RedisOperation;
import com.vipabc.vliveshow.apitest.bean.asset.request.Request;
import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;
import leo.carnival.workers.impl.ScriptExecutor;
import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

@SuppressWarnings({"unused", "DefaultAnnotationParam", "unchecked", "WeakerAccess", "MismatchedQueryAndUpdateOfCollection", "FieldCanBeLocal"})
public class TestAsset implements Serializable, Executor<Map<String, Object>, Map<String, Object>> {
    private static final Logger logger = Logger.getLogger(TestAsset.class);

    private String info;

    private Integer repeat = 1;

    private Request request;

    private Map<MongoOperation, DBObject> mongoOperation;

    private Map<RedisOperation, DBObject> redisOperation;

    private Map<SqlOperation, DBObject> sqlOperation;

    private Map<AssertType, Object> assertions;

    private Map<String, Object> extractions;

    private Map<String, Object> settings;

    public Request getRequest() {
        return request;
    }

    public String dbOperation(Map<String, Object> extractionMap) {
        String rtnString = null;
        if (mongoOperation != null && !mongoOperation.isEmpty() && extractionMap.containsKey("mongoConnection")) {
            DB dbConnection = (DB) extractionMap.get("mongoConnection");
            for (Map.Entry<MongoOperation, DBObject> entry : mongoOperation.entrySet())
                //last transaction effective
                rtnString = entry.getKey().setDBConnection(dbConnection).execute(entry.getValue());
        }
        else if(redisOperation != null && !redisOperation.isEmpty() && extractionMap.containsKey("redisConnection")){
            JedisCluster jedisCluster = (JedisCluster) extractionMap.get("redisConnection");
            for(Map.Entry<RedisOperation, DBObject> entry : redisOperation.entrySet())
                rtnString = entry.getKey().setDBConnection(jedisCluster).execute(entry.getValue());
        }
        else if(sqlOperation != null && !sqlOperation.isEmpty() && extractionMap.containsKey("sqlConnection")){
            Connection connection = (Connection) extractionMap.get("sqlConnection");
            for(Map.Entry<SqlOperation, DBObject> entry : sqlOperation.entrySet())
                rtnString = entry.getKey().setDBConnection(connection).execute(entry.getValue());
        }
        return rtnString;
    }


    public boolean evaluate(ResponseContainer responseContainer) {
        if (assertions == null)
            return false;
        for (Map.Entry<AssertType, Object> entry : assertions.entrySet())
            entry.getKey().setExpectedValue(entry.getValue()).evaluate(responseContainer);
        return false;
    }

    public void extract(ResponseContainer response, Map<String, Object> extractionMap) throws Exception {
        if (extractions == null)
            return;
        new JsonExtractor().go(extractions, (Map)response.getResponseObject(), extractionMap);
    }

    public void setting(Map<String, Object> extractionMap) {
        if (settings == null || settings.isEmpty())
            return;

        for (Map.Entry<String, Object> map : settings.entrySet()) {
            if (map.getValue() instanceof Number)
                extractionMap.put(map.getKey(), parserNumber(map.getValue()));
            else
                extractionMap.put(map.getKey(), map.getValue());
        }
    }

    protected Number parserNumber(Object obj) {
        try {
            return NumberFormat.getNumberInstance().parse(obj.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> extractionKVMap) {
        for (int i = 0; i < repeat; i++) {

            TestAsset asset = (TestAsset) InstanceUpdater.build(MapValueUpdater.build(extractionKVMap).setScriptEngine(ScriptExecutor.build())).process(this);

            if (asset.info != null)
                logger.info(asset.info);
            try {
                ResponseContainer responseContainer;
                if (asset.getRequest() != null) {
                    HttpResponse response = asset.getRequest().process();
                    responseContainer = new ResponseContainer(response);
                } else {
                    String json = asset.dbOperation(extractionKVMap);
                    responseContainer = new ResponseContainer(json);
                }
                asset.evaluate(responseContainer);
                asset.extract(responseContainer, extractionKVMap);
                asset.setting(extractionKVMap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return extractionKVMap;
    }


    @Override
    public String toString() {
        return request == null ? "" : request.toString();
    }
}
