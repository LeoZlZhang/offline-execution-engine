package leo.webapplication.repository;

import leo.webapplication.model.TestCaseCatalog;
import org.springframework.stereotype.Repository;

/**
 * Created by leo_zlzhang on 10/12/2016.
 * Repository for test case catalog
 */
@Repository
public class TestCaseCatalogRepository extends MongoRepository<TestCaseCatalog>{
    public TestCaseCatalogRepository() {
        super(TestCaseCatalog.class);
    }
}
