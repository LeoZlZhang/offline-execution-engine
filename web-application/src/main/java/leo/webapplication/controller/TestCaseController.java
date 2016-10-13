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
@RequestMapping("/v1/testcase")
public class TestCaseController {

    @Autowired
    TestCaseService testCaseService;


    /**
     * Load Apidata by source file name, which is test case file name in tree catalog
     *
     * @param sourceFileName souce file name, which is also the file name in tree catalog
     * @return JsonResponse
     */
    @RequestMapping(value = "/getbysourcefilename", method = RequestMethod.GET)
    public JsonResponse getApiDataBySourceFileName(@RequestParam("name") String sourceFileName) {
        if (sourceFileName == null || sourceFileName.isEmpty())
            return JsonResponse.fail("Source file name is empty");

        List<ApiData> result = testCaseService.getApiDataBySourceFileName(sourceFileName);

        return result != null && result.size() > 0 ?
                JsonResponse.success(JacksonUtils.fromObject2Map(result.get(0))) :
                JsonResponse.fail("not found test case");
    }

    /**
     * load ApiData by test case name
     *
     * @param name api data name
     * @return JsonResponse
     */
    @RequestMapping(value = "/getbyname", method = RequestMethod.GET)
    public JsonResponse getApiData(@RequestParam("name") String name) {
        if (name == null || name.isEmpty())
            return JsonResponse.fail("Test case name is empty");

        List<ApiData> result = testCaseService.getApiDataByName(name);

        return result != null && result.size() > 0 ?
                JsonResponse.success(JacksonUtils.fromObject2Map(result.get(0))) :
                JsonResponse.fail("not found test case");
    }

    /**
     * Get all api data
     *
     * @return JsonResponse
     */
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public JsonResponse getAllApiData() {

        List<ApiData> result = testCaseService.getApiDataOfAll();

        return result != null ?
                JsonResponse.success(JacksonUtils.fromObject2Map(result)) :
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
            return JsonResponse.fail("Empty test case");
        if (apiData.getName() == null || apiData.getName().isEmpty())
            return JsonResponse.fail("Test case name is empty");

        boolean result = testCaseService.saveApiData(apiData);

        return result ?
                JsonResponse.success(apiData.getName()) :
                JsonResponse.fail("fail to save test case");
    }

    @RequestMapping(value = "/deletebysourcefilename", method = RequestMethod.DELETE)
    public JsonResponse deleteApiData(@RequestParam("name") String sourceFileName) {
        if (sourceFileName == null || sourceFileName.isEmpty())
            return JsonResponse.fail("Source file name is empty");

        boolean result = testCaseService.deleteApiDataBySourceFileName(sourceFileName);

        return result ?
                JsonResponse.success("delete test case " + sourceFileName) :
                JsonResponse.fail("fail to delete test case");
    }

}
