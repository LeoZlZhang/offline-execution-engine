package leo.webapplication.controller;

import leo.webapplication.dto.JsonResponse;
import leo.webapplication.model.EEProfile;
import leo.webapplication.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by leo_zlzhang on 10/24/2016.
 * Profile api
 */
@SuppressWarnings({"unused", "SpringAutowiredFieldsWarningInspection"})
@RestController
@RequestMapping("/v1/profile")
public class ProfileController {


    @Autowired
    ProfileService profileService;


    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public JsonResponse getProfile(EEProfile profile) {
        if (profile == null)
            return JsonResponse.fail("profile is empty");

        List<EEProfile> result = profileService.loadProfile(profile);

        return result != null && result.size() > 0 ?
                JsonResponse.success(result) :
                JsonResponse.fail("not found profile");
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResponse saveProfile(@RequestBody EEProfile profile) {
        if (profile == null)
            return JsonResponse.fail("empty profile");

        boolean result = profileService.saveProfile(profile);

        return result ?
                JsonResponse.success(profile) :
                JsonResponse.fail("fail to save profile");
    }



    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public JsonResponse deleteProfile(@RequestBody EEProfile profile) {
        if (profile == null)
            return JsonResponse.fail("empty profile");


        Object[] result = profileService.deleteProfile(profile);

        return (boolean)result[0] ?
                JsonResponse.success(result[1]) :
                JsonResponse.fail(result[1].toString());
    }
}
