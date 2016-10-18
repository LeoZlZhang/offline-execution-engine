package leo.webapplication.service;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.model.TestCaseCatalog;
import leo.webapplication.repository.TestCaseCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by leo_zlzhang on 10/13/2016.
 * Catalog service
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class CatalogService {
    @Autowired
    TestCaseCatalogRepository testCaseCatalogRepository;

    public List<TestCaseCatalog> loadCatalog(TestCaseCatalog testCaseCatalog) {
        Map apiDataMap = JacksonUtils.fromObject2Map(testCaseCatalog);
        Query query = new Query();
        for (Object key : apiDataMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(apiDataMap.get(key)));

        return testCaseCatalogRepository.find(query);
    }


    public boolean saveCatalog(List<TestCaseCatalog> catalogs) {
        testCaseCatalogRepository.remove(new Query());
        for (TestCaseCatalog catalog : catalogs)
            testCaseCatalogRepository.save(catalog);
        return true;
    }
}
