package leo.webapplication.repository;

import leo.webapplication.model.ApiData;
import org.springframework.stereotype.Repository;

/**
 * Created by leozhang on 9/10/16.
 * Mongo repository for api test
 */
@Repository
public class ApiTestMongoRepository extends MongoRepository<ApiData>{
    public ApiTestMongoRepository() {
        super(ApiData.class);
    }
}
