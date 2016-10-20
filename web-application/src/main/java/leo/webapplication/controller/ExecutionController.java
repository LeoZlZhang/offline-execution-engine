package leo.webapplication.controller;

import leo.engineCore.engineFoundation.Gear.Gear;
import leo.engineCore.testEngine.TestEngine;
import leo.engineData.logging.EELogger;
import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.ApiData;
import leo.webapplication.service.GearService;
import leo.webapplication.service.TestCaseService;
import leo.webapplication.service.WebSocketService;
import leo.webapplication.util.ExecutionLogPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by leo_zlzhang on 10/17/2016.
 * Execution controller
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "Convert2Lambda"})
@RestController
@RequestMapping("/v1/execution")
public class ExecutionController {


    @Autowired
    WebSocketService webSocketService;

    @Autowired
    TestCaseService testCaseService;

    @Autowired
    GearService gearService;




    @RequestMapping(value = "/go", method = RequestMethod.POST)
    public JsonResponse executeByData(@RequestBody ApiData apiData){
        if(apiData == null)
            return JsonResponse.fail("empty api data");

        if(apiData.getId() != null && !apiData.getId().isEmpty()) {
            List<ApiData> datas = testCaseService.getApiData(apiData);
            if (datas == null || datas.size() == 0)
                return JsonResponse.fail("not found test data in mongo: " + apiData.getId());
            apiData = datas.get(0);
        }

        Gear gear = gearService.getOneGear();
        if (gear == null)
            return JsonResponse.fail("not found gear in mongo");

        ApiData finalApiData = apiData;
        finalApiData.setCustomLogger(EELogger.getLogger(ApiData.class).setWorker(ExecutionLogPublisher.build("test", webSocketService)));

        new Thread(new Runnable() {
            @Override
            public void run() {
                TestEngine.build(gear).execute(finalApiData).Assert();
            }
        }).run();

        return JsonResponse.success(null);
    }
}
