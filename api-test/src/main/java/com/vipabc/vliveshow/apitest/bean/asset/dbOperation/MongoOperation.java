package com.vipabc.vliveshow.apitest.bean.asset.dbOperation;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import leo.carnival.workers.prototype.Executor;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by leo_zlzhang on 9/18/2016.
 * CRUD to mongo
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public enum MongoOperation implements Serializable, Executor<DBObject, String> {

    create {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty()
                    || dbObject.getValues() == null || dbObject.getValues().isEmpty())
                throw new RuntimeException("Invalid create operation to mongo, miss table or values");

            DBCollection collection = getCollection(dbObject.getTable());

            BasicDBObject newDoc = buildDoc(dbObject.getValues());

            logger.info(String.format("[%d] mongo operation insert into %s new doc %s", Thread.currentThread().getId(), collection, newDoc.toString()));

            return collection.insert(newDoc).toString();
        }
    },
    query {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty()
                    || dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid query to mongo, miss table or criteria");

            DBCollection collection = getCollection(dbObject.getTable());

            BasicDBObject query = buildDoc(dbObject.getCriteria());

            logger.info(String.format("[%d] mongo operation findOne from %s where %s", Thread.currentThread().getId(), collection, query.toString()));

            com.mongodb.DBObject result = collection.findOne(query);
            return result == null ? "{}" : result.toString();
        }
    },
    update {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty()
                    || dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty()
                    || dbObject.getValues() == null || dbObject.getValues().isEmpty())
                throw new RuntimeException("Invalid update to mongo, miss table or criteria or values");

            DBCollection collection = getCollection(dbObject.getTable());

            BasicDBObject query = buildDoc(dbObject.getCriteria());

            BasicDBObject updateDoc = buildDoc(dbObject.getValues());

            logger.info(String.format("[%d] mongo operation update %s where %s set doc %s", Thread.currentThread().getId(), collection, query.toString(), updateDoc.toString()));

            return collection.update(query, new BasicDBObject("$set", updateDoc)).toString();
        }
    },
    delete {
        @Override
        public String execute(DBObject dbObject) {
            if (dbObject == null)
                throw new RuntimeException("dbObject should not be empty");
            if (dbObject.getTable() == null || dbObject.getTable().isEmpty()
                    || dbObject.getCriteria() == null || dbObject.getCriteria().isEmpty())
                throw new RuntimeException("Invalid delete to mongo, miss table or criteria");

            DBCollection collection = getCollection(dbObject.getTable());

            BasicDBObject query = buildDoc(dbObject.getCriteria());

            logger.info(String.format("[%d] mongo operation delete from %s where %s", Thread.currentThread().getId(), collection, query.toString()));

            return collection.remove(query).toString();
        }
    };

    protected Logger logger = Logger.getLogger(MongoOperation.class);
    private DB db;

    public MongoOperation setDBConnection(DB dbConnection) {
        this.db = dbConnection;
        return this;
    }

    protected DBCollection getCollection(String name) {
        DBCollection collection = db.getCollection(name);

        if (collection == null)
            throw new RuntimeException(String.format("Not found collection:%s in mongo", name));
        return collection;
    }

    protected BasicDBObject buildDoc(Map<String, Object> map) {
        if (map == null || map.isEmpty())
            throw new RuntimeException("Empty value for mongo operation");

        BasicDBObject doc = new BasicDBObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("_id"))
                doc.append(entry.getKey(), new ObjectId(entry.getValue().toString()));
            else
                doc.append(entry.getKey(), entry.getValue());
        }
        return doc;

    }

    public MongoOperation setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }
}
