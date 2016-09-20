package com.vipabc.vliveshow.apitest.TestFlow;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.vipabc.vliveshow.apitest.bean.APITestData;
import com.vipabc.vliveshow.apitest.bean.asset.TestAsset;
import leo.carnival.MyArrayUtils;
import org.apache.log4j.Logger;
import org.postgresql.jdbc42.Jdbc42Connection;
import org.testng.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"unused", "ConstantConditions"})
public class APITest {
    private static final Logger logger = Logger.getLogger(APITest.class);


    public Object[] prepare() throws IOException, ClassNotFoundException, SQLException {
        URL url = this.getClass().getClassLoader().getResource("db.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(url.getFile())));

        Object[] rtnObjects = new Object[3];

        //mongo
        String mongoHost = properties.getProperty("mongo.host");
        String mongoPort = properties.getProperty("mongo.port");
        String mongoDataBase = properties.getProperty("mongo.database");
        if (mongoHost != null && !mongoHost.isEmpty() &&
                mongoPort != null && !mongoPort.isEmpty() &&
                mongoDataBase != null && !mongoDataBase.isEmpty()) {
            MongoClient mongoConnection = new MongoClient(mongoHost, Integer.valueOf(mongoPort));
            rtnObjects[0] = mongoConnection.getDB(mongoDataBase);
        }

        //redis
        String redisHosts = properties.getProperty("redis.hosts");
        if (redisHosts != null && !redisHosts.isEmpty()) {
            Set<HostAndPort> nodes = new HashSet<>();
            for (String host : redisHosts.trim().split(",")) {
                String[] hs = host.split(":");
                nodes.add(new HostAndPort(hs[0], Integer.valueOf(hs[1])));
            }
            JedisCluster redisConnection = new JedisCluster(nodes, 5000, 1000);
            rtnObjects[1] = redisConnection;
        }

        //sql
        String sqlDriverClass = properties.getProperty("sql.driver.class");
        String sqlUrl = properties.getProperty("sql.url");
        String sqlUser = properties.getProperty("sql.user");
        String sqlPwd = properties.getProperty("sql.password");
        if (sqlDriverClass != null && !sqlDriverClass.isEmpty() &&
                sqlUrl != null && !sqlUrl.isEmpty() &&
                sqlUser != null && !sqlUser.isEmpty() &&
                sqlPwd != null && !sqlPwd.isEmpty()) {
            Class.forName(sqlDriverClass);
            Connection sqlConnection = DriverManager.getConnection(sqlUrl, sqlUser, sqlPwd);
            rtnObjects[2] = sqlConnection;
        }

        return rtnObjects;
    }

    public void end() {
        logger.info("End");
    }

    public Object[] loadTestData(APITestData tc) {
        logger.info(tc.toString());
        return new Object[]{tc};
    }

    public Object[] initializeLoop(APITestData tc, DB mongoConnection, JedisCluster redisConnection, Jdbc42Connection sqlConnection) {
        Assert.assertTrue(tc.getAssets().length > 0);
        Map<String, Object> map = new HashMap<>();
        map.put("mongoConnection", mongoConnection);
        map.put("redisConnection", redisConnection);
        map.put("sqlConnection", sqlConnection);
        return new Object[]{tc.getAssets()[0], map};
    }

    public Object[] loadNextTestAsset(APITestData tc, TestAsset asset) {
        int index = MyArrayUtils.getElementIndexInArray(tc.getAssets(), asset);
        Assert.assertTrue(index >= 0);

        if ((index + 1) == tc.getAssets().length)
            return new Object[]{asset, true};
        else
            return new Object[]{tc.getAssets()[index + 1], false};
    }


    public Object[] requestWithHttpClient(TestAsset asset, Map<String, Object> extractionKVMap) throws Exception {
        asset.execute(extractionKVMap);
        return new Object[]{extractionKVMap};
    }
}
