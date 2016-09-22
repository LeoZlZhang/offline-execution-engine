package com.vipabc.vliveshow.apitest.bean.asset;


import com.google.api.client.http.HttpResponse;
import com.mongodb.DB;
import com.vipabc.vliveshow.apitest.Util.JsonExtractor;
import com.vipabc.vliveshow.apitest.bean.asset.assertions.AssertType;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.DBObject;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.MongoOperation;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.RedisOperation;
import com.vipabc.vliveshow.apitest.bean.asset.dbOperation.SqlOperation;
import com.vipabc.vliveshow.apitest.bean.asset.request.Request;
import leo.carnival.workers.impl.Executors;
import leo.carnival.workers.impl.JsonUtils.InstanceUpdater;
import leo.carnival.workers.impl.JsonUtils.MapValueUpdater;
import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.sql.Connection;
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


    public String dbOperation(Map<String, Object> extractionMap) {
        String rtnString = null;
        if (mongoOperation != null && !mongoOperation.isEmpty() && extractionMap.containsKey("mongoConnection")) {
            DB dbConnection = (DB) extractionMap.get("mongoConnection");
            for (Map.Entry<MongoOperation, DBObject> entry : mongoOperation.entrySet())
                //last transaction effective
                rtnString = entry.getKey().setDBConnection(dbConnection).execute(entry.getValue());
        } else if (redisOperation != null && !redisOperation.isEmpty() && extractionMap.containsKey("redisConnection")) {
            JedisCluster jedisCluster = (JedisCluster) extractionMap.get("redisConnection");
            for (Map.Entry<RedisOperation, DBObject> entry : redisOperation.entrySet())
                rtnString = entry.getKey().setDBConnection(jedisCluster).execute(entry.getValue());
        } else if (sqlOperation != null && !sqlOperation.isEmpty() && extractionMap.containsKey("sqlConnection")) {
            Connection connection = (Connection) extractionMap.get("sqlConnection");
            for (Map.Entry<SqlOperation, DBObject> entry : sqlOperation.entrySet())
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
        new JsonExtractor().go(extractions, (Map) response.getResponseObject(), extractionMap);
    }

    public void setting(Map<String, Object> extractionMap) {
        if (settings == null || settings.isEmpty())
            return;

        for (Map.Entry<String, Object> map : settings.entrySet())
            extractionMap.put(map.getKey(), map.getValue());
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> extractionKVMap) {
        for (int i = 0; i < repeat; i++) {

            TestAsset asset = (TestAsset) InstanceUpdater.build(MapValueUpdater.build(extractionKVMap).setScriptEngine(Executors.scriptExecutor())).process(this);

            if (asset.info != null)
                logger.info(asset.info);
            try {
                ResponseContainer responseContainer = null;
                if (asset.getRequest() != null) {
                    HttpResponse response = asset.getRequest().process();
                    responseContainer = new ResponseContainer(response);
                } else if (asset.getMongoOperation() != null || asset.getRedisOperation() != null || asset.getSqlOperation() != null) {
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


    /**
     * Getter
     */
    public String getInfo() {
        return info;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public Request getRequest() {
        return request;
    }

    public Map<MongoOperation, DBObject> getMongoOperation() {
        return mongoOperation;
    }

    public Map<RedisOperation, DBObject> getRedisOperation() {
        return redisOperation;
    }

    public Map<SqlOperation, DBObject> getSqlOperation() {
        return sqlOperation;
    }

    public Map<AssertType, Object> getAssertions() {
        return assertions;
    }

    public Map<String, Object> getExtractions() {
        return extractions;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    /**
     * Setter
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setMongoOperation(Map<MongoOperation, DBObject> mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    public void setRedisOperation(Map<RedisOperation, DBObject> redisOperation) {
        this.redisOperation = redisOperation;
    }

    public void setSqlOperation(Map<SqlOperation, DBObject> sqlOperation) {
        this.sqlOperation = sqlOperation;
    }

    public void setAssertions(Map<AssertType, Object> assertions) {
        this.assertions = assertions;
    }

    public void setExtractions(Map<String, Object> extractions) {
        this.extractions = extractions;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }
}
