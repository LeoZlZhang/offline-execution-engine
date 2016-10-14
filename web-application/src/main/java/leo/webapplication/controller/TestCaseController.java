package leo.webapplication.controller;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.ApiData;
import leo.webapplication.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by leozhang on 8/31/16.
 * Controller of test case operation
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/v1/apidata")
public class TestCaseController {

    @Autowired
    TestCaseService testCaseService;


    /**
     * Load api data refer to certain key of api data class
     *
     * @param apiData api data with certain key to query with
     * @return JsonResponse
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JsonResponse getApiDataById(ApiData apiData) {
        if (apiData == null)
            return JsonResponse.fail("api data is empty");

        List<ApiData> result = testCaseService.getApiData(apiData);

        return result != null && result.size() > 0 ?
                JsonResponse.success(JacksonUtils.fromObject2List(result)) :
                JsonResponse.fail("not found test case");
    }


    /**
     * Persistent api data to mongo
     *
     * @param apiData api data to save
     * @return JsonReponse
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResponse saveApiData(@RequestBody ApiData apiData) {
        if (apiData == null)
            return JsonResponse.fail("empty test case");

        boolean result = testCaseService.saveApiData(apiData);

        return result ?
                JsonResponse.success(JacksonUtils.fromObject2Map(apiData)) :
                JsonResponse.fail("fail to save test case");
    }


    /**
     * delete api data by id
     *
     * @param apiData api data should have id for deleting
     * @return JsonResponse
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public JsonResponse deleteApiData(ApiData apiData) {
        if (apiData == null)
            return JsonResponse.fail("empty api data");


        Object[] result = testCaseService.deleteApiData(apiData);

        return (boolean)result[0] ?
                JsonResponse.success(result[1]) :
                JsonResponse.fail(result[1].toString());
    }

}
