package com.vipabc.vliveshow.apitest.TestFlow;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.vipabc.vliveshow.apitest.bean.APITestData;
import org.apache.log4j.Logger;
import org.postgresql.jdbc42.Jdbc42Connection;
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

@SuppressWarnings({"unused", "ConstantConditions", "MismatchedQueryAndUpdateOfCollection"})
public class APITest {
    private static final Logger logger = Logger.getLogger(APITest.class);


    public Object[] prepare() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        URL url = this.getClass().getClassLoader().getResource("db.properties");
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

    public void printTCName(APITestData apiTestData){
        logger.info(String.format("Execute test case: \"%s\" ---> \"%s\"", apiTestData.getSourceFileName(), apiTestData.getName()));
    }

    public Object[] initializeLoop(DB mongoConnection, JedisCluster redisConnection, Jdbc42Connection sqlConnection) {
        Map<String, Object> map = new HashMap<>();
        map.put("mongoConnection", mongoConnection);
        map.put("redisConnection", redisConnection);
        map.put("sqlConnection", sqlConnection);
        return new Object[]{map};
    }


    /**
     * Requesting assertion extraction, including db operation
     * @param apiTestData this is test data which contains all information of testing
     * @param extractionMap contain necessary db connection, extraction map is also a container inside the apiTestData to store extracted value from response
     */
    public void requestWithHttpClient(APITestData apiTestData, Map<String, Object> extractionMap) {
        apiTestData.execute(extractionMap);
    }


    public void end() {
        logger.info("End");
    }
}
