package leo.webapplication.controller;

import leo.webapplication.model.MyTestData;
import org.springframework.web.bind.annotation.*;

/**
 * Created by leozhang on 8/31/16.
 * ...
 */
@RestController
public class ExecutionController {


    @RequestMapping(value = "/v1/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(name = "name") String name){
        return "hello " + name;
    }


    @SuppressWarnings("ConstantConditions")
    @RequestMapping(value = "/v1/try/model", method = RequestMethod.POST)
    public String tryModel(@RequestBody MyTestData myTestData){
        System.out.println(myTestData == null);
        return myTestData.process(null);
    }

}
