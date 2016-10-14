package leo.webapplication.controller;

import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.TestCaseCatalog;
import leo.webapplication.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by leo_zlzhang on 10/13/2016.
 * Catalog API
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/v1/catalog")
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    /**
     * Load catalog
     *
     * @return JsonResponse
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public JsonResponse loadCatalog(TestCaseCatalog testCaseCatalog) {
        if (testCaseCatalog == null)
            return JsonResponse.fail("empty catalog");

        List<TestCaseCatalog> result = catalogService.loadCatalog(testCaseCatalog);

        return result != null && result.size() > 0 ?
                JsonResponse.success(result) :
                JsonResponse.fail("fail to load catalog");
    }

    /**
     * Persistent catalog to mongo
     *
     * @param catalogs catalog to save
     * @return JsonResponse
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResponse saveCatalog(@RequestBody List<TestCaseCatalog> catalogs) {
        if (catalogs == null)
            return JsonResponse.fail("Catalog is null", null);

        boolean result = catalogService.saveCatalog(catalogs);

        return result ?
                JsonResponse.success(null) :
                JsonResponse.fail("fail to save catalog");
    }
}
