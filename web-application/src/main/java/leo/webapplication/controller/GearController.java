package leo.webapplication.controller;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.EEGear;
import leo.webapplication.service.GearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by leo_zlzhang on 10/24/2016.
 * Gear api
 */
@SuppressWarnings({"unused", "SpringAutowiredFieldsWarningInspection"})
@RestController
@RequestMapping("/v1/gear")
public class GearController {


    @Autowired
    GearService gearService;


    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public JsonResponse getGear(EEGear gear) {
        if (gear == null)
            return JsonResponse.fail("gear is empty");

        List<EEGear> result = gearService.loadGear(gear);

        return result != null && result.size() > 0 ?
                JsonResponse.success(JacksonUtils.fromObject2List(result)) :
                JsonResponse.fail("not found gear");
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResponse saveGear(@RequestBody EEGear gear) {
        if (gear == null)
            return JsonResponse.fail("empty gear");

        boolean result = gearService.saveGear(gear);

        return result ?
                JsonResponse.success(JacksonUtils.fromObject2Map(gear)) :
                JsonResponse.fail("fail to save gear");
    }



    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public JsonResponse deleteGear(@RequestBody EEGear gear) {
        if (gear == null)
            return JsonResponse.fail("empty gear");


        Object[] result = gearService.deleteGear(gear);

        return (boolean)result[0] ?
                JsonResponse.success(result[1]) :
                JsonResponse.fail(result[1].toString());
    }
}
