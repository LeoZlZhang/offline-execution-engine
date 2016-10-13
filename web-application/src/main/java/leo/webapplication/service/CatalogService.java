package leo.webapplication.service;

import leo.webapplication.model.TestCaseCatalog;
import leo.webapplication.repository.TestCaseCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by leo_zlzhang on 10/13/2016.
 * Catalog service
 */
@Service
public class CatalogService {
    @Autowired
    TestCaseCatalogRepository testCaseCatalogRepository;

    public List<TestCaseCatalog> loadCatalog() {
        return testCaseCatalogRepository.findAll();
    }


    public boolean saveCatalog(List<TestCaseCatalog> catalogs) {
        testCaseCatalogRepository.remove(new Query());
        for (TestCaseCatalog catalog : catalogs)
            testCaseCatalogRepository.save(catalog);
       return true;
    }
}
