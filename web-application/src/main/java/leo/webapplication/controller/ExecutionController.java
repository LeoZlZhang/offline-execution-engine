package leo.webapplication.controller;

import leo.engineCore.testEngine.TestEngine;
import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.ApiData;
import leo.webapplication.model.EEGear;
import leo.webapplication.model.EEProfile;
import leo.webapplication.service.GearService;
import leo.webapplication.service.ProfileService;
import leo.webapplication.service.TestCaseService;
import leo.webapplication.service.WebSocketService;
import leo.webapplication.util.EELogger;
import leo.webapplication.util.ExecutionLogPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    ProfileService profileService;


    @RequestMapping(value = "/go", method = RequestMethod.POST)
    public JsonResponse executeByData(@RequestParam(name = "gearId") String gearId,
                                      @RequestParam(name = "profileId") String profileId,
                                      @RequestBody ApiData apiData) {
        if (apiData == null)
            return JsonResponse.fail("empty api data");
        if (apiData.getChannel() == null || apiData.getChannel().isEmpty())
            return JsonResponse.fail("web socket channel not defined");

        List<EEGear> gears = gearService.loadGear(new EEGear(gearId));
        if (gears == null || gears.size() == 0)
            return JsonResponse.fail("not found gear in mongo");
        if (gears.size() > 1)
            return JsonResponse.fail("found more than one gear in mongo");

        List<EEProfile> profiles = profileService.loadProfile(new EEProfile(profileId));
        if (profiles == null || profiles.size() == 0)
            return JsonResponse.fail("not found profile in mongo");
        if (profiles.size() > 1)
            return JsonResponse.fail("found more than one profile in mongo");

        new Thread(new Runnable() {
            @Override
            public void run() {
                EELogger webSocketLogger = EELogger.getLogger(ApiData.class).setWorker(ExecutionLogPublisher.build(apiData.getChannel(), webSocketService));
                TestEngine engine = TestEngine.build(gears.get(0));
                engine.setCustomLogger(webSocketLogger);
                engine.execute("BeforeTestFlow");
                engine.execute(apiData.update(profiles.get(0).getProfile())).AssertForWeb(webSocketLogger);
                engine.execute("AfterTestFlow");
            }
        }).start();

        return JsonResponse.success(null);
    }
}
