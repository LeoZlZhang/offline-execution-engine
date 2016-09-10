package leo.webapplication.repository;

import leo.webapplication.model.ApiTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by leozhang on 9/10/16.
 * Mongo
 */

@Repository
public class MongoRepository {

    @Autowired
    MongoTemplate mongoTemplate;


    @RequestMapping(value = "/ideas", method = RequestMethod.GET)
    public String[] getJob(@RequestParam("name") String name) {
        Criteria c = Criteria.where("name").is(name);
        Query query = new Query(c);
        return mongoTemplate.findOne(query, ApiTestData.class).getIdeas();

    }
}
