package leo.webapplication.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by leozhang on 9/10/16.
 * Mongo
 */

@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "WeakerAccess", "unused"})
public abstract class MongoRepository<T> {

    @Autowired
    MongoTemplate mongoTemplate;


    private Class<T> cls;

    public MongoRepository(Class<T> cls) {
        this.cls = cls;
    }


    public void save(T entity){
        mongoTemplate.save(entity);
    }




    public List<T> find(Query query) {
        return mongoTemplate.find(query, cls);
    }

    public T findOne(Query query) {
        return mongoTemplate.findOne(query, cls);
    }

    public T findById(String id){
        return mongoTemplate.findById(id, cls);
    }


    public void updateFirst(Query query, Update update){
        mongoTemplate.updateFirst(query, update, cls);
    }


    public void remove(Query query){
        mongoTemplate.remove(query, cls);
    }
}
