package leo.webapplication.controller;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.ApiData;
import leo.webapplication.model.TestCaseCatalog;
import leo.webapplication.repository.ApiTestMongoRepository;
import leo.webapplication.repository.TestCaseCatalogRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by leozhang on 8/31/16.
 * ...
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "ConstantConditions", "unused", "WeakerAccess"})
@RestController
@RequestMapping("/try")
public class ExecutionController {


    @Autowired
    ApiTestMongoRepository apiTestMongoRepository;


    @Autowired
    TestCaseCatalogRepository testCaseCatalogRepository;


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "leo") String name) {
        System.out.println("hello");
        if (name.equalsIgnoreCase("1"))
            throw new RuntimeException("create a run time exception");
        return "hello " + name;
    }


//    @RequestMapping(value = "/model", method = RequestMethod.POST)
//    public String tryModel(@RequestBody ApiTestData myTestData) {
//        System.out.println(myTestData == null);
//        return myTestData.process(null);
//    }

//    @ExceptionHandler(RuntimeException.class)
//    public Map exception(Exception exception, Model model) {
//        model.addAttribute("exception", exception);
//        Map map = new HashMap();
//        map.put("a", "exception");
//        return map;
//    }

    @RequestMapping(value = "/api/data/get/byname", method = RequestMethod.GET)
    public
    @ResponseBody
    JsonResponse getApiData(@RequestParam("name") String name) {
        Criteria c = Criteria.where("name").is(name);
        Query query = new Query(c);
        ApiData apiData = apiTestMongoRepository.findOne(query);

        System.out.println(JacksonUtils.toJson(apiData));
        return JsonResponse.success(JacksonUtils.fromJsonObject(JacksonUtils.toJson(apiData), Map.class));
    }


    @RequestMapping(value = "/api/data/get/all", method = RequestMethod.GET)
    public
    @ResponseBody
    JsonResponse getAllApiData() {

        List<ApiData> apiData = apiTestMongoRepository.findAll();

        return JsonResponse.success(JacksonUtils.fromJsonObject(JacksonUtils.toJson(apiData), Map.class));
    }

    @RequestMapping(value = "/api/data/save", method = RequestMethod.POST)
    public String saveApiData(@RequestBody ApiData apiData) {
        System.out.println(JacksonUtils.toJson(apiData));
        apiTestMongoRepository.save(apiData);
        return apiData.getName();

    }

    @RequestMapping(value = "/api/catalog/load", method = RequestMethod.GET)
    public JsonResponse loadTestCaseCatalog() {
        List<TestCaseCatalog> testCaseCatalogs = testCaseCatalogRepository.findAll();
        TestCaseCatalog testCaseCatalog = testCaseCatalogs == null || testCaseCatalogs.size() == 0 ? new TestCaseCatalog() : testCaseCatalogs.get(0);
        return JsonResponse.success(JacksonUtils.fromJsonObject(JacksonUtils.toJson(testCaseCatalog), Map.class));
    }

    @RequestMapping(value = "/api/catalog/save", method = RequestMethod.POST)
    public JsonResponse saveTestCaseCatalog(@RequestBody TestCaseCatalog catalog) {
        if (catalog == null)
            return JsonResponse.fail("Catalog is null", null);

        if (catalog.getId() != null)
            testCaseCatalogRepository.remove(new Query(Criteria.where("_id").is(new ObjectId(catalog.getId()))));
        testCaseCatalogRepository.save(catalog);

        return JsonResponse.success(null);
    }
}
